package com.mx.testcore.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

data class ErrorApi(
    @SerializedName("error") var error: String = "",
    @SerializedName("code") var code: String = "",
    @SerializedName("msg") var msg: String = "",
    @SerializedName("message") var message: String = "",
    @Transient val errorT: Throwable?
)

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val code: Int? = null,
        val error: Throwable? = null,
        val errorBody: ErrorApi? = null
    ) : ResultWrapper<Nothing>()
    data class NetworkError(
        val code:Int,
        val message:String
    ) : ResultWrapper<Nothing>()
}

suspend fun <T> manageApiCall(
    context: Context? = null,
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is SocketTimeoutException ->{
                    ResultWrapper.NetworkError(
                        code = 1,//UTConstants.NETWORK_ERROR_TIMEOUT,
                        message = "No se ha podido establecer comunicación con el servidor."
                    )
                }
                is IOException -> {
                    if (isConnectedToNetwork(context)) {
                        ResultWrapper.NetworkError(
                            1,//UTConstants.NETWORK_ERROR_API,
                            message = "No se ha podido establecer comunicación con el servidor, verifica tu conexión o VPN."
                        )
                    } else {
                        ResultWrapper.NetworkError(
                            code = 1,//UTConstants.NETWORK_ERROR_CONNECTION,
                            message = "Verifica la conexión de tu dispositivo")
                    }
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, throwable, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

private fun isConnectedToNetwork(context: Context?) : Boolean{
    if (context == null) return false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_VPN)-> {
                return true
            }
        }
    }
    return false
}

private fun convertErrorBody(throwable: HttpException): ErrorApi? {
    return try {
        val type = object : TypeToken<ErrorApi>() {}.type
        var errorObject: ErrorApi? = null
        throwable.response()?.errorBody()?.charStream()?.let {
            errorObject = GsonBuilder().setLenient().create().fromJson(it, type)
        }
        errorObject
    } catch (exception: Exception) {
        null
    }
}
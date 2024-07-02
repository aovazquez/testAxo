package com.mx.testcore.data.repository

import android.content.Context
import com.mx.testcore.data.models.Product
import com.mx.testcore.data.network.MainApiImplementation
import com.mx.testcore.data.room.AppDao
import com.mx.testcore.data.room.AppEntity
import com.mx.testcore.utils.manageApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: MainApiImplementation,
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getProducts() = manageApiCall(context, dispatcher) {api.getProducts()}

}
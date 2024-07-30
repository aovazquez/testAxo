package com.mx.testcore.di

import com.mx.testcore.data.network.MainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @DSSLSocketFactory sslSocketFactory: SSLSocketFactory,
        @TrustManager509X trustManager509: X509TrustManager
    ) =
        OkHttpClient.Builder()
            .connectTimeout(5000L, TimeUnit.SECONDS)
            .readTimeout(5000L, TimeUnit.SECONDS)
            .writeTimeout(5000L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .sslSocketFactory(sslSocketFactory, trustManager509)
            .hostnameVerifier { _, _ -> true }
            .build()

    @Provides
    @Singleton
    @DSSLSocketFactory
    fun provideSocketFactory(@TrustManager509X trustManager: X509TrustManager): SSLSocketFactory {
        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }

    @Provides
    @Singleton
    @TrustManager509X
    fun provideTrustManager(): X509TrustManager = object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate?>?,
            authType: String?
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate?>?,
            authType: String?
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMainApiClient(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }
}
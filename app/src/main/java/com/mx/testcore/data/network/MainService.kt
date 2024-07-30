package com.mx.testcore.data.network

import com.mx.testcore.data.models.MainProduct
import retrofit2.http.GET

interface MainService {

    @GET("/products")
    suspend fun getProducts(): MainProduct
}
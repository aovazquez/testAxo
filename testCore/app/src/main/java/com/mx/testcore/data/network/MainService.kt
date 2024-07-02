package com.mx.testcore.data.network

import com.mx.testcore.data.models.MainProduct
import com.mx.testcore.data.models.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainService {

    @GET("/products")
    suspend fun getProducts(): MainProduct
}
package com.mx.testcore.data.network

import com.mx.testcore.data.models.MainProduct
import com.mx.testcore.data.models.Product

interface MainApi {
    suspend fun getProducts(): MainProduct
}
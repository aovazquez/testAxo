package com.mx.testcore.data.network

import com.mx.testcore.data.models.MainProduct

interface MainApi {
    suspend fun getProducts(): MainProduct
}
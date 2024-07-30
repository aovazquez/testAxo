package com.mx.testcore.data.network

import com.mx.testcore.data.models.MainProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainApiImplementation @Inject constructor(
    private val api: MainService
): MainApi {

    override suspend fun getProducts(): MainProduct = withContext(Dispatchers.IO) {
        api.getProducts()
    }

}
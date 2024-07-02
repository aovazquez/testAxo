package com.mx.testcore.domain

import com.mx.testcore.data.models.MainProduct
import com.mx.testcore.data.models.Product
import com.mx.testcore.data.repository.MainRepository
import com.mx.testcore.utils.ResultWrapper
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(): ResultWrapper<MainProduct> {
        return repository.getProducts()
    }
}
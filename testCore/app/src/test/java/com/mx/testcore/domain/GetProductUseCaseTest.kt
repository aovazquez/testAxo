package com.mx.testcore.domain

import com.mx.testcore.data.models.MainProduct
import com.mx.testcore.data.models.Product
import com.mx.testcore.data.repository.MainRepository
import com.mx.testcore.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetProductUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: MainRepository

    lateinit var getProductUseCase: GetProductUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getProductUseCase = GetProductUseCase( repository )
    }

    @Test
    fun `when the api doesnt return anything then get error`() = runBlocking {
        // Given
        coEvery { repository.getProducts() } returns ResultWrapper.Success(MainProduct(products = emptyList()))

        //When
        val response = getProductUseCase()

        when (response) {
            is ResultWrapper.GenericError -> {
                println("GenericError: ${response.error} - ${response.code} - ${response.errorBody}")
                throw AssertionError("Expected success but got error")
            }
            is ResultWrapper.NetworkError -> {
                println("NetworkError: ${response.message}")
                throw AssertionError("Expected success but got error")
            }
            is ResultWrapper.Success -> {
                println("Success: ${response.value}")
                assert(response.value.products.isEmpty())
            }
        }

    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        // Given
        val productsList = listOf(Product(id = 1, title = "mesa", description = "descripción pequeña", price = 20.0, thumbnail = "", image = emptyList()))
        coEvery { repository.getProducts() } returns ResultWrapper.Success(MainProduct(products = productsList))

        //When
        val response = getProductUseCase()

        when (response) {
            is ResultWrapper.GenericError -> {
                println("GenericError: ${response.error} - ${response.code} - ${response.errorBody}")
                throw AssertionError("Expected success but got error")
            }
            is ResultWrapper.NetworkError -> {
                println("NetworkError: ${response.message}")
                throw AssertionError("Expected success but got error")
            }
            is ResultWrapper.Success -> {
                println("Success: ${response.value}")
                assert(productsList == response.value.products)
            }
        }
    }
}

package com.mx.testcore.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mx.testcore.data.models.MainProduct
import com.mx.testcore.data.models.Product
import com.mx.testcore.domain.GetProductUseCase
import com.mx.testcore.ui.UiState
import com.mx.testcore.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @RelaxedMockK
    private lateinit var getProductUseCase: GetProductUseCase

    private lateinit var mainViewModel: MainViewModel

    @get: Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel( getProductUseCase )
        Dispatchers.setMain( Dispatchers.Unconfined )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodels is created at the first time, get all products and set all values`() = runTest {
        // Given
        val productsList = listOf(
            Product(id = 1, title = "mesa", description = "descripción pequeña", price = 20.0, thumbnail = "", image = emptyList()),
            Product(id = 2, title = "silla", description = "descripción silla", price = 30.0, thumbnail = "", image = emptyList()))
        coEvery { getProductUseCase() } returns ResultWrapper.Success(MainProduct(products = productsList))

        //When
        mainViewModel.getProducts()

        //Then
        when (mainViewModel.getProductsUiState.value) {
            is UiState.Failure -> {
                println("GenericError: error")
                throw AssertionError("Expected success but got error")
            }
            is UiState.Loading -> {
                println("UI Loading")
            }
            is UiState.Success -> {
                assert( (mainViewModel.getProductsUiState.value as UiState.Success<List<Product>>).data[0] == productsList.first() )
            }
            null -> {
                println("null data")
            }
        }
        // assert( mainViewModel. )
    }
}
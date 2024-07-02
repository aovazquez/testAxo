package com.mx.testcore.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.testcore.data.models.Product
import com.mx.testcore.domain.GetProductUseCase
import com.mx.testcore.ui.UiState
import com.mx.testcore.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    val getProductsUiState = MutableLiveData<UiState<List<Product>>>()

    fun getProducts() {
        viewModelScope.launch {
            getProductsUiState.value = UiState.Loading
            val result = getProductUseCase()
            when ( result ) {
                is ResultWrapper.GenericError -> {
                    if ( result.error?.message == "HTTP 401 Unauthorized" ) {
                        getProductsUiState.value = UiState.Failure(result.errorBody?.message)
                    } else {
                        getProductsUiState.value = UiState.Failure(result.errorBody?.message)
                    }
                }
                is ResultWrapper.NetworkError -> {
                    getProductsUiState.value = UiState.Failure("Network Error")
                }
                is ResultWrapper.Success -> {
                    getProductsUiState.value = UiState.Success( result.value.products!! )
                }
            }
        }
    }
}
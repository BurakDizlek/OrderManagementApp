package com.bd.ordermanagementapp.screens.orders.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.model.ResultCodes
import com.bd.data.repository.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderDetailsViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailsUiViewState())
    val uiState: StateFlow<OrderDetailsUiViewState> = _uiState.asStateFlow()

    fun getOrderId(orderId: String) {
        try {
            _uiState.value = _uiState.value.copy(loading = true, errorMessage = null)
            viewModelScope.launch {
                val result = repository.getOrderById(orderId = orderId)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.value = _uiState.value.copy(order = result.data)
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = result.message)
                }
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.localizedMessage)
        } finally {
            _uiState.value = _uiState.value.copy(loading = false)
        }
    }
}
package com.bd.ordermanagementapp.screens.orders.create.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bd.data.model.ResultCodes
import com.bd.data.model.order.CreateOrderData
import com.bd.data.repository.order.OrderRepository
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: OrderRepository
) : ViewModel() {

    val argsData = savedStateHandle.toRoute<CreateOrderRoute.DetailEntry>()

    private val _uiState = MutableStateFlow(OrderDetailEntryUiViewState())
    val uiState: StateFlow<OrderDetailEntryUiViewState> = _uiState.asStateFlow()

    fun createOrder(address: String, note: String) {
        try {
            _uiState.update { it.copy(loading = true, errorMessage = null) }
            viewModelScope.launch {
                val result = repository.createOrder(
                    CreateOrderData(
                        isQuickOrder = argsData.isQuickOrder,
                        menuItemId = argsData.menuItemId,
                        latitude = argsData.latitude,
                        longitude = argsData.longitude,
                        address = address,
                        note = note
                    )
                )

                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(loading = false, createdOrder = result.data) }
                } else {
                    _uiState.update { it.copy(loading = false, errorMessage = result.message) }
                }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(loading = false, errorMessage = e.message) }
        }
    }

    fun navigated(){
        _uiState.update { it.copy(createdOrder = null) }
    }
}
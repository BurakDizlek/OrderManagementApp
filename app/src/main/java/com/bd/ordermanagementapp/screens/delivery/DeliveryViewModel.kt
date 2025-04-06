package com.bd.ordermanagementapp.screens.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.model.ResultCodes
import com.bd.data.model.order.FilterOrderData
import com.bd.data.repository.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeliveryViewModel(
    private val orderRepository: OrderRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeliveryUiState())
    val uiState: StateFlow<DeliveryUiState> = _uiState.asStateFlow()

    fun getDeliveryOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, errorMessage = null) }
            try {
                val result =
                    orderRepository.getFilteredOrders(filterOrderData = _uiState.value.filterData)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(loading = false, orders = result.data) }
                } else {
                    _uiState.update { it.copy(loading = false, errorMessage = result.message) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        errorMessage = e.localizedMessage.orEmpty()
                    )
                }
            }
        }
    }

    fun onFilterChanged(newFilter: FilterOrderData) {
        val needsRefresh = _uiState.value.filterData != newFilter
        _uiState.update { it.copy(filterData = newFilter) }
        if (needsRefresh) {
            getDeliveryOrders()
        }
    }
}
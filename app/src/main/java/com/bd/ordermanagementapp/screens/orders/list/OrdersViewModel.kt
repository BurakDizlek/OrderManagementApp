package com.bd.ordermanagementapp.screens.orders.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.model.ResultCodes
import com.bd.data.model.order.FilterOrderData
import com.bd.data.repository.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(OrdersUiViewState())
    val uiState: StateFlow<OrdersUiViewState> = _uiState.asStateFlow()

    fun getOrders() {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value.copy(loading = true, errorMessage = null, isOrdersEmpty = false)
                val result = repository.getFilteredOrders(
                    filterOrderData = FilterOrderData(
                        query = _uiState.value.filterData.query,
                        fromTime = _uiState.value.filterData.fromTime,
                        toTime = _uiState.value.filterData.toTime,
                        statuses = _uiState.value.filterData.statuses
                    )
                )
                if (result.code == ResultCodes.SUCCESS) {
                    if (result.data.isEmpty()) {
                        _uiState.value =
                            _uiState.value.copy(isOrdersEmpty = true, orders = listOf())
                    } else {
                        _uiState.value = _uiState.value.copy(orders = result.data)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = result.message)

                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.localizedMessage)
            } finally {
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }
    }

    fun onFilterDataChanged(filterOrderData: FilterOrderData) {
        val needToGetOrders = _uiState.value.filterData != filterOrderData
        _uiState.value = _uiState.value.copy(filterData = filterOrderData)
        if (needToGetOrders) {
            getOrders()
        }
    }
}
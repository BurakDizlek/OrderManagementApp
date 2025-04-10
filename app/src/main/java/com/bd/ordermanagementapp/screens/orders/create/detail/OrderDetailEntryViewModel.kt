package com.bd.ordermanagementapp.screens.orders.create.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bd.data.model.ResultCodes
import com.bd.data.model.order.CreateOrderData
import com.bd.data.repository.geocoding.GeocodingRepository
import com.bd.data.repository.order.OrderRepository
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: OrderRepository,
    private val geocodingRepository: GeocodingRepository,
    private val userBottomBarManager: UserBottomBarManager,
) : ViewModel() {

    val argsData = savedStateHandle.toRoute<CreateOrderRoute.DetailEntry>()

    private val _uiState = MutableStateFlow(OrderDetailEntryUiViewState())
    val uiState: StateFlow<OrderDetailEntryUiViewState> = _uiState.asStateFlow()

    fun createOrder(note: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(loading = true, errorMessage = null) }

                val result = repository.createOrder(
                    CreateOrderData(
                        isQuickOrder = argsData.isQuickOrder,
                        menuItemId = argsData.menuItemId,
                        latitude = argsData.latitude,
                        longitude = argsData.longitude,
                        address = _uiState.value.address,
                        note = note
                    )
                )

                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(loading = false, createdOrder = result.data) }
                    if (!argsData.isQuickOrder) {
                        userBottomBarManager.onCartCountChanged(0)
                    }
                } else {
                    _uiState.update { it.copy(loading = false, errorMessage = result.message) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false, errorMessage = e.message) }
            }
        }
    }

    fun clearCreateOrderErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun navigated() {
        _uiState.update { it.copy(createdOrder = null) }
    }

    fun getAddress() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                val result = geocodingRepository.getAddress(
                    latitude = argsData.latitude, longitude = argsData.longitude
                )
                onAddressChanged(result?.data.orEmpty())
            } catch (e: Exception) {
            } finally {
                _uiState.update { it.copy(loading = false) }
            }
        }
    }

    fun onAddressChanged(address: String) {
        if (address != _uiState.value.address) {
            _uiState.update { it.copy(address = address) }
        }
    }
}
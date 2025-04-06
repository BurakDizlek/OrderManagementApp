package com.bd.ordermanagementapp.screens.orders.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.core.session.SessionManager
import com.bd.core.session.UserType
import com.bd.data.model.ResultCodes
import com.bd.data.repository.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val repository: OrderRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailsUiViewState())
    val uiState: StateFlow<OrderDetailsUiViewState> = _uiState.asStateFlow()

    fun getOrderId(orderId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true, errorMessage = null)

                val result = repository.getOrderById(orderId = orderId)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.value = _uiState.value.copy(order = result.data)
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

    fun cancelOrder(orderId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true, errorCancelMessage = null)
                val result = if (isManager()) {
                    repository.rejectOrder(orderId = orderId)
                } else {
                    repository.cancelOrder(orderId = orderId)
                }
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.value = _uiState.value.copy(order = result.data)
                } else {
                    _uiState.value = _uiState.value.copy(errorCancelMessage = result.message)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorCancelMessage = e.localizedMessage)
            } finally {
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }
    }

    fun clearCancelErrorMessage() {
        _uiState.value = _uiState.value.copy(errorCancelMessage = null)
    }

    fun confirmOrderReceived(orderId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true, errorConfirmMessage = null)
                val result = repository.completeDelivery(orderId = orderId)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.value = _uiState.value.copy(order = result.data)
                } else {
                    _uiState.value = _uiState.value.copy(errorConfirmMessage = result.message)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorConfirmMessage = e.localizedMessage)
            } finally {
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }
    }

    fun clearConfirmErrorMessage() {
        _uiState.value = _uiState.value.copy(errorConfirmMessage = null)
    }

    fun isManager(): Boolean {
        return sessionManager.getUserType() == UserType.RESTAURANT_MANAGER
    }

    fun startDelivery(orderId: String) {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value.copy(loading = true, errorStartDeliveryMessage = null)
                val result = repository.startDelivery(orderId = orderId)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.value = _uiState.value.copy(order = result.data)
                } else {
                    _uiState.value = _uiState.value.copy(errorStartDeliveryMessage = result.message)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorStartDeliveryMessage = e.localizedMessage)
            } finally {
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }
    }

    fun clearStartDeliveryErrorMessage() {
        _uiState.value = _uiState.value.copy(errorStartDeliveryMessage = null)
    }
}
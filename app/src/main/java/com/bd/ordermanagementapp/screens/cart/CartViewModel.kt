package com.bd.ordermanagementapp.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.core.session.SessionManager
import com.bd.data.extensions.orZero
import com.bd.data.model.ResultCodes
import com.bd.data.repository.cart.CartRepository
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val userBottomBarManager: UserBottomBarManager,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiViewState())
    val uiState: StateFlow<CartUiViewState> = _uiState.asStateFlow()

    fun fetchCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingCart = true) }
            try {
                val result = cartRepository.getCart()
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(cart = result.data, loadingCart = false) }
                    updateCartCount(count = result.data?.cartItems?.size)
                } else {
                    _uiState.update {
                        it.copy(
                            errorCart = result.message,
                            loadingCart = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorCart = e.message, loadingCart = false) }
            }
        }
    }

    fun addToCart(menuItemId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingCart = true, errorAddToCartMessage = null) }
            try {
                val result = cartRepository.addToCart(menuItemId = menuItemId)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(cart = result.data, loadingCart = false) }
                    updateCartCount(count = result.data?.cartItems?.size)
                } else {
                    _uiState.update {
                        it.copy(
                            errorAddToCartMessage = result.message,
                            loadingCart = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorAddToCartMessage = e.message, loadingCart = false) }
            }
        }
    }

    fun deleteFromCart(menuItemId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingCart = true, errorDeleteFromCartMessage = null) }
            try {
                val result = cartRepository.deleteFromCart(menuItemId = menuItemId)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(cart = result.data, loadingCart = false) }
                    updateCartCount(count = result.data?.cartItems?.size)
                } else {
                    _uiState.update {
                        it.copy(
                            errorDeleteFromCartMessage = result.message,
                            loadingCart = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorDeleteFromCartMessage = e.message,
                        loadingCart = false
                    )
                }
            }
        }
    }

    private fun updateCartCount(count: Int?) {
        viewModelScope.launch {
            userBottomBarManager.onCartCountChanged(count.orZero())
        }
    }

    fun onNeedToLoginDialogDismiss() {
        if (_uiState.value.displayNeedLoginDialog) {
            _uiState.update { it.copy(displayNeedLoginDialog = false) }
        }
    }

    fun orderButtonClicked() {
        if (sessionManager.isUserLoggedIn()) {
            _uiState.update { it.copy(navigateToOrder = true) }
        } else {
            _uiState.update { it.copy(displayNeedLoginDialog = true) }
        }
    }

    fun onOrderNavigationCompleted() {
        if (_uiState.value.navigateToOrder) {
            _uiState.update { it.copy(navigateToOrder = false) }
        }
    }
}
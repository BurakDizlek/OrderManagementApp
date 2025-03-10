package com.bd.ordermanagementapp.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.model.ResultCodes
import com.bd.data.repository.cart.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiViewState())
    val uiState: StateFlow<CartUiViewState> = _uiState.asStateFlow()

    fun fetchCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingCart = true) }
            try {
                val result = cartRepository.getCart()
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(cart = result.data, loadingCart = false) }
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
}
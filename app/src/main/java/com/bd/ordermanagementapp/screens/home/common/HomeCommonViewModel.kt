package com.bd.ordermanagementapp.screens.home.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.core.session.SessionManager
import com.bd.data.extensions.orZero
import com.bd.data.model.ResultCodes
import com.bd.data.usecase.AddToCartUseCase
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class HomeCommonViewModel(
    private val addToCartUseCase: AddToCartUseCase,
    private val userBottomBarManager: UserBottomBarManager,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _commonUiState = MutableStateFlow(HomeCommonUiViewState())
    val commonUiState: StateFlow<HomeCommonUiViewState> = _commonUiState.asStateFlow()

    fun onAddButtonClicked(menuItemId: Int) {
        _commonUiState.update { it.copy(orderOrCartDecisionMenuItemId = menuItemId) }
    }

    fun onOrderOrCartDecisionDialogDismiss() {
        if (_commonUiState.value.orderOrCartDecisionMenuItemId != null)
            _commonUiState.update { it.copy(orderOrCartDecisionMenuItemId = null) }
    }

    fun onOrderNowButtonClicked(menuItemId: Int) {
        if (sessionManager.isUserLoggedIn()) {
            _commonUiState.update {
                it.copy(
                    orderOrCartDecisionMenuItemId = null,
                    quickOrderMenuItemId = menuItemId
                )
            }
        } else {
            _commonUiState.update {
                it.copy(
                    orderOrCartDecisionMenuItemId = null,
                    displayNeedLoginDialog = true
                )
            }
        }
    }

    fun onQuickOrderNavigationCompleted() {
        if (_commonUiState.value.quickOrderMenuItemId != null)
            _commonUiState.update { it.copy(quickOrderMenuItemId = null) }
    }

    fun onNeedToLoginDialogDismiss() {
        if (_commonUiState.value.displayNeedLoginDialog) {
            _commonUiState.update {
                it.copy(displayNeedLoginDialog = false)
            }
        }
    }

    fun onErrorOkButtonClicked() {
        _commonUiState.update { it.copy(errorAddToCart = null) }
    }

    fun onAddToCartClicked(menuItemId: Int) {
        _commonUiState.update { it.copy(orderOrCartDecisionMenuItemId = null) }
        viewModelScope.launch {
            _commonUiState.update { it.copy(loadingAddToCart = true) }
            val result = addToCartUseCase(menuItemId = menuItemId)
            if (result.code == ResultCodes.SUCCESS) {
                userBottomBarManager.onCartCountChanged(result.data?.cartItems?.count().orZero())
                _commonUiState.update { it.copy(cart = result.data, loadingAddToCart = false) }
            } else {
                _commonUiState.update {
                    it.copy(
                        errorAddToCart = result.message,
                        loadingAddToCart = false
                    )
                }
            }
        }
    }
}
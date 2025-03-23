package com.bd.ordermanagementapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.extensions.orZero
import com.bd.data.model.ResultCodes
import com.bd.data.repository.campaign.CampaignRepository
import com.bd.data.repository.menu.MenuRepository
import com.bd.data.usecase.AddToCartUseCase
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val campaignRepository: CampaignRepository,
    private val menuRepository: MenuRepository,
    private val addToCartUseCase: AddToCartUseCase,
    private val userBottomBarManager: UserBottomBarManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiViewState())
    val uiState: StateFlow<HomeUiViewState> = _uiState.asStateFlow()

    fun fetchCampaigns() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingCampaigns = true) }
            try {
                val result = campaignRepository.getAllCampaigns()
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(campaigns = result.data, loadingCampaigns = false) }
                } else {
                    _uiState.update {
                        it.copy(
                            errorCampaigns = result.message,
                            loadingCampaigns = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorCampaigns = e.message, loadingCampaigns = false) }
            }
        }
    }

    fun fetchMenuItems() {
        if (_uiState.value.loadingMenuItems || !_uiState.value.hasMoreMenuItems) return
        handleMenuLoading(isLoading = true)

        viewModelScope.launch {
            try {
                val result = menuRepository.getPaginatedMenuItems(
                    page = _uiState.value.currentPage,
                    pageSize = _uiState.value.pageSize
                )
                delay(1000) //Delay to show there is a lazy loading.
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update {
                        it.copy(
                            errorMenuItems = null,
                            menuItems = it.menuItems + result.data.items,
                            hasMoreMenuItems = it.currentPage < result.data.totalPages,
                            currentPage = it.currentPage + 1
                        )
                    }
                } else {
                    handleMenuError(message = result.message)
                }
            } catch (e: Exception) {
                handleMenuError(e.message.toString())
            } finally {
                handleMenuLoading(isLoading = false)
            }
        }
    }

    private fun handleMenuError(message: String) {
        _uiState.update { currentUIState ->
            currentUIState.copy(
                errorMenuItems = message.takeIf { currentUIState.menuItems.isEmpty() },
                loadingMenuItems = false
            )
        }
    }

    private fun handleMenuLoading(isLoading: Boolean) {
        _uiState.update { it.copy(loadingMenuItems = isLoading) }
    }

    fun onAddButtonClicked(menuItemId: Int) {
        _uiState.update { it.copy(orderOrCartDecisionMenuItemId = menuItemId) }
    }

    fun onOrderOrCartDecisionDialogDismiss() {
        _uiState.update { it.copy(orderOrCartDecisionMenuItemId = null) }
    }

    fun onOrderNowButtonClicked(menuItemId: Int) {
        _uiState.update { it.copy(orderOrCartDecisionMenuItemId = null) }
    }

    fun onErrorOkButtonClicked() {
        _uiState.update { it.copy(errorOrderOrCart = null) }
    }

    fun onAddToCartClicked(menuItemId: Int) {
        _uiState.update { it.copy(orderOrCartDecisionMenuItemId = null) }
        viewModelScope.launch {
            _uiState.update { it.copy(loadingOrderOrCart = true) }
            val result = addToCartUseCase(menuItemId = menuItemId)
            if (result.code == ResultCodes.SUCCESS) {
                userBottomBarManager.onCartCountChanged(result.data?.cartItems?.count().orZero())
                _uiState.update { it.copy(cart = result.data, loadingOrderOrCart = false) }
            } else {
                _uiState.update {
                    it.copy(
                        errorOrderOrCart = result.message,
                        loadingOrderOrCart = false
                    )
                }
            }
        }
    }
}
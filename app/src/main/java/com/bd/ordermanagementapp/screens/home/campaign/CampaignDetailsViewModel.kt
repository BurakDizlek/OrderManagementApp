package com.bd.ordermanagementapp.screens.home.campaign

import androidx.lifecycle.viewModelScope
import com.bd.core.session.SessionManager
import com.bd.data.model.ResultCodes
import com.bd.data.repository.menu.MenuRepository
import com.bd.data.usecase.AddToCartUseCase
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import com.bd.ordermanagementapp.screens.home.common.HomeCommonViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignDetailsViewModel(
    private val menuRepository: MenuRepository,
    addToCartUseCase: AddToCartUseCase,
    userBottomBarManager: UserBottomBarManager,
    sessionManager: SessionManager
) : HomeCommonViewModel(addToCartUseCase, userBottomBarManager, sessionManager) {

    private val _uiState = MutableStateFlow(CampaignDetailsUiViewState())
    val uiState: StateFlow<CampaignDetailsUiViewState> = _uiState.asStateFlow()

    fun fetchMenuItems(menuItemIds: String) {
        _uiState.update { it.copy(loadingMenuItems = true) }
        viewModelScope.launch {
            try {
                val result = menuRepository.getMenuItemByIds(ids = menuItemIds)
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update {
                        it.copy(
                            menuItems = result.data,
                            errorMenuItems = null,
                        )
                    }
                } else {
                    _uiState.update { it.copy(errorMenuItems = result.message) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMenuItems = e.message) }
            } finally {
                _uiState.update { it.copy(loadingMenuItems = false) }
            }
        }
    }

    fun showDataNullError() {
        _uiState.update { it.copy(showDataNullError = true) }

    }
}
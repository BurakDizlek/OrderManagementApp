package com.bd.ordermanagementapp.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.core.session.SessionManager
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionManager: SessionManager,
    private val userBottomBarManager: UserBottomBarManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiViewState())
    val uiState: StateFlow<ProfileUiViewState> = _uiState


    fun checkLoggedIn() {
        if (sessionManager.isUserLoggedIn()) {
            _uiState.value = ProfileUiViewState(userName = sessionManager.getUserName())
        } else {
            _uiState.value = ProfileUiViewState(userName = null)
        }
    }

    fun logout() {
        sessionManager.clearToken()
        viewModelScope.launch {
            userBottomBarManager.onUserTypeChanged(sessionManager.getUserType())
            userBottomBarManager.onCartCountChanged(0)
        }
        checkLoggedIn()
    }
}
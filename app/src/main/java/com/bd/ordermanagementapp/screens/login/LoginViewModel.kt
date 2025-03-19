package com.bd.ordermanagementapp.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.core.session.SessionManager
import com.bd.data.model.ResultCodes
import com.bd.data.repository.login.LoginRepository
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val sessionManager: SessionManager,
    private val userBottomBarManager: UserBottomBarManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiViewState())
    val uiState: StateFlow<LoginUiViewState> = _uiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                val result = repository.login(username, password)
                if (result?.code == ResultCodes.SUCCESS) {
                    sessionManager.saveToken(result.data.orEmpty())
                    val loggedInUserType = sessionManager.getUserType()
                    userBottomBarManager.onUserTypeChanged(loggedInUserType)
                    _uiState.update {
                        it.copy(
                            successMessage = result.message,
                            loggedInUserType = loggedInUserType,
                            loading = false,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = result?.message,
                            loading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, loading = false) }
            }
        }
    }
}
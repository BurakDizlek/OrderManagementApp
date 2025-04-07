package com.bd.ordermanagementapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.core.session.UserType
import com.bd.data.repository.notification.NotificationRepository
import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import com.bd.ordermanagementapp.data.notification.NotificationDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val userBottomBarManager: UserBottomBarManager,
    private val notificationDataProvider: NotificationDataProvider,
    private val notificationRepository: NotificationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiViewState())
    val uiState: StateFlow<MainUiViewState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userBottomBarManager.updateBottomBar()
            userBottomBarManager.bottomBarStartDestinationRoute.collectLatest { route ->
                _uiState.update {
                    it.copy(bottomBarStartDestinationRoute = route)
                }
            }
        }

        viewModelScope.launch {
            userBottomBarManager.bottomBarOptionsFlow.collectLatest { bottomBarOptions ->
                _uiState.update {
                    it.copy(bottomBarScreens = bottomBarOptions)
                }
            }
        }

        viewModelScope.launch {
            userBottomBarManager.currentUserType.collectLatest { userType ->
                try {
                    if (userType != UserType.NOT_DEFINED) {
                        notificationRepository.saveDevice(notificationDataProvider.getFCMToken())
                    }
                } catch (e: Exception) {
                    print(e.message)
                }
            }
        }
    }
}
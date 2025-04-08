package com.bd.ordermanagementapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.repository.notification.NotificationRepository
import com.bd.ordermanagementapp.data.notification.NotificationDataProvider
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val notificationRepository: NotificationRepository,
    private val notificationDataProvider: NotificationDataProvider,
) : ViewModel() {
    fun registerDevice() {
        viewModelScope.launch {
            try {
                notificationRepository.saveDevice(notificationDataProvider.getFCMToken())
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }
}
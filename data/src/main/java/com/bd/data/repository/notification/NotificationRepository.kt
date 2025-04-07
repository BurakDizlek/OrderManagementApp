package com.bd.data.repository.notification

import com.bd.data.model.BaseResult

interface NotificationRepository {
    suspend fun saveDevice(fcmToken: String): BaseResult<Boolean>
}
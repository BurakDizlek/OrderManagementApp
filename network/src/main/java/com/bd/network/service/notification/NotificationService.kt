package com.bd.network.service.notification

import com.bd.network.model.BaseResponse

interface NotificationService {
    suspend fun saveDevice(fcmToken: String): BaseResponse<Boolean?>
}
package com.bd.data.repository.notification

import com.bd.data.model.BaseResult
import com.bd.data.model.ResultCodes
import com.bd.network.service.notification.NotificationService

class NotificationRepositoryImpl(private val service: NotificationService) :
    NotificationRepository {
    override suspend fun saveDevice(fcmToken: String): BaseResult<Boolean> {
        val response = service.saveDevice(fcmToken)
        val isSuccess = response.data == true
        return BaseResult(
            data = isSuccess,
            message = response.message.orEmpty(),
            code = if (isSuccess) ResultCodes.SUCCESS else ResultCodes.FAILURE
        )
    }
}
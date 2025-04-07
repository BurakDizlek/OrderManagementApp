package com.bd.network.service.notification

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.notification.NotificationSaveDeviceRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val NOTIFICATION_URL = "$BASE_URL/notification"

class NotificationServiceImpl(private val client: HttpClient) : NotificationService {
    override suspend fun saveDevice(fcmToken: String): BaseResponse<Boolean?> {
        return handleErrors {
            client.post("${NOTIFICATION_URL}/save-device") {
                setBody(NotificationSaveDeviceRequest(fcmToken = fcmToken))
                contentType(ContentType.Application.Json)
            }
        }
    }
}
package com.bd.network.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSaveDeviceRequest(val fcmToken: String)
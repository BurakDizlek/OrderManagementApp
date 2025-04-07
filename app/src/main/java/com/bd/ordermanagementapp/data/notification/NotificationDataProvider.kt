package com.bd.ordermanagementapp.data.notification

interface NotificationDataProvider {
    fun saveFCMToken(token: String)
    fun getFCMToken(): String
}
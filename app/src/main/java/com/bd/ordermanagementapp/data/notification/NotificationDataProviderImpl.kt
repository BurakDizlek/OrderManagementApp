package com.bd.ordermanagementapp.data.notification

import com.bd.core.prefs.SecurePreferences

private const val FCM_TOKEN_KEY = "fcm_token"

class NotificationDataProviderImpl(private val securePreferences: SecurePreferences) :
    NotificationDataProvider {

    override fun saveFCMToken(token: String) {
        securePreferences.saveString(FCM_TOKEN_KEY, token)
    }

    override fun getFCMToken(): String {
        return securePreferences.getString(FCM_TOKEN_KEY, "").orEmpty()
    }
}
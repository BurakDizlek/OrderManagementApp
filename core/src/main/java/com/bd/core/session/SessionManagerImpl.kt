package com.bd.core.session

import android.util.Base64
import com.bd.core.prefs.SecurePreferences
import com.google.gson.Gson


private const val TOKEN_KEY = "token"
private const val USER_NAME_KEY = "user_name"
private const val USER_TYPE_KEY = "user_type"

private const val CUSTOMER_UNIT_NAME = "Customers"
private const val RESTAURANT_MANAGER_UNIT_NAME = "Managers"

class SessionManagerImpl(private val securePreferences: SecurePreferences) : SessionManager {

    override fun saveToken(token: String) {
        securePreferences.saveString(TOKEN_KEY, token)
        saveUserNameAndType(token)
    }

    override fun getToken(): String {
        return securePreferences.getString(TOKEN_KEY, "").orEmpty()
    }

    override fun clearToken() {
        saveToken(token = "")
        clearUserNameAndType()
    }

    override fun isUserLoggedIn(): Boolean {
        return getToken().isNotEmpty()
    }

    override fun getUserName(): String {
        return securePreferences.getString(USER_NAME_KEY, "").orEmpty()
    }

    override fun getUserType(): UserType {
        val stringUserType = securePreferences.getString(USER_TYPE_KEY, UserType.NOT_DEFINED.name)
            ?: UserType.NOT_DEFINED.name
        return UserType.valueOf(stringUserType)
    }

    private fun saveUserNameAndType(token: String) {
        getJWT(token = token)?.let {
            securePreferences.saveString(USER_NAME_KEY, it.userName)
            when (it.unit) {
                CUSTOMER_UNIT_NAME -> {
                    securePreferences.saveString(USER_TYPE_KEY, UserType.CUSTOMER.name)
                }

                RESTAURANT_MANAGER_UNIT_NAME -> {
                    securePreferences.saveString(USER_TYPE_KEY, UserType.RESTAURANT_MANAGER.name)
                }

                else -> {
                    securePreferences.saveString(USER_TYPE_KEY, UserType.NOT_DEFINED.name)
                }
            }
        }
    }

    private fun clearUserNameAndType() {
        securePreferences.saveString(USER_NAME_KEY, "")
        securePreferences.saveString(USER_TYPE_KEY, UserType.NOT_DEFINED.name)
    }

    private fun getJWT(token: String): JWT? {
        try {
            val splitArray = token.split(".")
            return Gson().fromJson(
                String(Base64.decode(splitArray[1], Base64.URL_SAFE)),
                JWT::class.java
            )
        } catch (e: Exception) {
            return null
        }
    }
}
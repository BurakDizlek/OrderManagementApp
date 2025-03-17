package com.bd.core.session

interface SessionManager {

    fun saveToken(token: String)

    fun getToken(): String

    fun clearToken()

    fun isUserLoggedIn(): Boolean

    fun getUserName(): String

    fun getUserType(): UserType
}
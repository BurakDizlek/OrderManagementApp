package com.bd.data.repository.login

import com.bd.data.model.BaseResult

interface LoginRepository {
    suspend fun login(username: String, password: String): BaseResult<String?>?
}
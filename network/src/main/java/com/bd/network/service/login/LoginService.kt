package com.bd.network.service.login

import com.bd.network.model.BaseResponse
import com.bd.network.model.LoginResponse

interface LoginService {
    suspend fun login(
        username: String,
        password: String
    ): BaseResponse<LoginResponse?>
}
package com.bd.data.repository.login

import com.bd.data.model.BaseResult
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.network.service.login.LoginService

class LoginRepositoryImpl(private val loginService: LoginService) : LoginRepository {
    override suspend fun login(
        username: String,
        password: String
    ): BaseResult<String?>? {
        val response = loginService.login(username = username, password = password)

        return BaseResult(
            code = response.code ?: NO_CODE,
            message = response.message.orEmpty(),
            data = response.data?.token
        )
    }
}
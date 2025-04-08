package com.bd.network.service.login

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.LoginRequest
import com.bd.network.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val LOGIN_URL = "$BASE_URL/login"

class LoginServiceImpl(private val httpClient: HttpClient) : LoginService {
    override suspend fun login(
        username: String,
        password: String,
        deviceId: String,
    ): BaseResponse<LoginResponse?> {
        return handleErrors {
            val clientWithAuth = httpClient.config {
                install(Auth) {
                    basic {
                        credentials {
                            sendWithoutRequest { true }
                            BasicAuthCredentials(
                                username = username,
                                password = password
                            )
                        }
                    }
                }
            }
            clientWithAuth.post(LOGIN_URL) {
                setBody(LoginRequest(deviceId = deviceId))
                contentType(ContentType.Application.Json)
            }
        }
    }
}
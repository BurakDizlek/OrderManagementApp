package com.bd.network

import com.bd.core.session.SessionManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class AppHttpClient(
    private val httpLogger: HttpLogger,
    private val sessionManager: SessionManager
) {
    fun create(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = httpLogger
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                header("Authorization", "Bearer ${sessionManager.getToken()}")
            }
        }
    }
}
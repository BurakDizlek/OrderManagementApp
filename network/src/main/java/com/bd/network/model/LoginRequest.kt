package com.bd.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val deviceId: String)
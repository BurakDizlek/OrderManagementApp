package com.bd.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val code: Int?,
    val message: String?,
    val data: T? = null
)

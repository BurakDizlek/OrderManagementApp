package com.bd.data.model

data class BaseResult<T>(
    val data: T, val message: String, val code: Int
)
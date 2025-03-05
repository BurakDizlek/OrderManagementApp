package com.bd.network.model

import kotlinx.serialization.Serializable


@Serializable
data class PaginatedResponse<T>(
    val items: List<T>?,
    val page: Int?,
    val pageSize: Int?,
    val totalItems: Int?,
    val totalPages: Int?
)
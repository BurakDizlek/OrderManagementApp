package com.bd.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItemDto(
    val id: Int?,
    val barcode: String?,
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    val lastPrice: Double?,
    val firstPrice: Double?,
    val currency: String?,
    val discount: Double?,
    val category: List<String>?
)
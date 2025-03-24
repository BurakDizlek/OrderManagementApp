package com.bd.network.model.order

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    val isQuickOrder: Boolean,
    val menuItemId: Int?,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val note: String?
)
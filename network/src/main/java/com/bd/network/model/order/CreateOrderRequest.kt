package com.bd.network.model.order

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    val isQuickOrder: Boolean,
    val menuItemId: Int?,
    val latitude: Long,
    val longitude: Long,
    val address: String,
    val note: String?
)
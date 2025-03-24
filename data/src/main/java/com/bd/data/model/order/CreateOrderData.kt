package com.bd.data.model.order

data class CreateOrderData(
    val isQuickOrder: Boolean,
    val menuItemId: Int?,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val note: String?
)
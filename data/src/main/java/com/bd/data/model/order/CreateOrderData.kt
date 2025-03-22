package com.bd.data.model.order

data class CreateOrderData(
    val isQuickOrder: Boolean,
    val menuItemId: Int?,
    val latitude: Long,
    val longitude: Long,
    val address: String,
    val note: String?
)
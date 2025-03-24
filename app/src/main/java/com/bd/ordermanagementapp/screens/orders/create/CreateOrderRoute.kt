package com.bd.ordermanagementapp.screens.orders.create

import kotlinx.serialization.Serializable

sealed class CreateOrderRoute {
    @Serializable
    data class Starter(
        val isQuickOrder: Boolean,
        val menuItemId: Int?
    ) : CreateOrderRoute()

    @Serializable
    data class DetailEntry(
        val isQuickOrder: Boolean,
        val menuItemId: Int?,
        val longitude: Double,
        val latitude: Double
    ) : CreateOrderRoute()

    @Serializable
    data class Success(val orderId: String) : CreateOrderRoute()
}
package com.bd.ordermanagementapp.screens.orders.create.detail

import kotlinx.serialization.Serializable


@Serializable
data class OrderDetailEntryData(
    val isQuickOrder: Boolean,
    val menuItemId: Int?,
    val longitude: Double,
    val latitude: Double
)
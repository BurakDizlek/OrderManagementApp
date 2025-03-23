package com.bd.ordermanagementapp.screens.orders.create

import kotlinx.serialization.Serializable

@Serializable
data class LocationPickerScreenData(
    val isQuickOrder: Boolean,
    val menuItemId: Int?
)
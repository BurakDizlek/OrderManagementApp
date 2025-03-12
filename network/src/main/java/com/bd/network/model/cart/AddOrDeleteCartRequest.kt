package com.bd.network.model.cart

import kotlinx.serialization.Serializable

@Serializable
data class AddOrDeleteCartRequest(
    private val menuItemId: Int
)
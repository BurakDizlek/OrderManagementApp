package com.bd.network.model.cart

import kotlinx.serialization.Serializable

@Serializable
data class AddOrDeletePublicCartRequest(
    private val deviceId: String,
    private val menuItemId: Int,
)
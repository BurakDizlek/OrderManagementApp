package com.bd.network.model.cart

import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val cartItems: List<CartItemDto>?,
    val totalPrice: Double?,
    val currency: String?
)
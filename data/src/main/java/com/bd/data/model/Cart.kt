package com.bd.data.model

import com.bd.data.extensions.orZero
import com.bd.network.model.cart.CartResponse

data class Cart(
    val cartItems: List<CartItem>,
    val totalPrice: Double,
    val currency: String
)

fun CartResponse.toCart(): Cart {
    return Cart(
        cartItems = cartItems?.map { it.toCartItem() }.orEmpty(),
        totalPrice = totalPrice.orZero(),
        currency = currency.orEmpty()
    )
}

fun Cart?.orEmpty(): Cart {
    return this ?: Cart(
        cartItems = listOf(),
        totalPrice = 0.0,
        currency = ""
    )
}
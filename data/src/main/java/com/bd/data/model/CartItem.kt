package com.bd.data.model

import com.bd.data.extensions.orZero
import com.bd.network.model.cart.CartItemDto

data class CartItem(
    val menuItem: MenuItem,
    val quantity: Int,
)

fun CartItemDto.toCartItem(): CartItem {
    return CartItem(
        menuItem = menuItem?.toMenuItem().orEmpty(),
        quantity = quantity.orZero()
    )
}

fun CartItem?.orEmpty(): CartItem {
    return CartItem(
        menuItem = this?.menuItem.orEmpty(),
        quantity = this?.quantity.orZero()
    )
}

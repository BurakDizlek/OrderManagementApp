package com.bd.network.model.cart

import com.bd.network.model.MenuItemDto
import kotlinx.serialization.Serializable

@Serializable
class CartItemDto(
    val menuItem: MenuItemDto?,
    val quantity: Int?,
)
package com.bd.data.model.order

import com.bd.data.extensions.orZero
import com.bd.network.model.order.OrderItem

data class OrderMenuItem(
    val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val quantity: Int,
)

fun OrderItem?.toOrderMenuItem(): OrderMenuItem {
    return OrderMenuItem(
        id = this?.id.orZero(),
        name = this?.name.orEmpty(),
        price = this?.price.orZero(),
        currency = this?.currency.orEmpty(),
        quantity = this?.quantity.orZero()
    )
}
package com.bd.data.model.order

import com.bd.data.extensions.orZero
import com.bd.network.model.order.OrderDto

data class Order(
    val id: String,
    val contactName: String,
    val contactNumber: String,
    val deliveryAddress: String,
    val latitude: Double,
    val longitude: Double,
    val orderItems: List<OrderMenuItem>,
    val totalPrice: Double,
    val currency: String,
    val status: OrderStatus,
    val statusText: String,
    val orderCreatedTime: Long,
    val statusChangedTime: Long,
    val note: String? = null,
    val content: String,
)

fun OrderDto?.toOrder(): Order {
    return Order(
        id = this?.id.orEmpty(),
        contactName = this?.contactName.orEmpty(),
        contactNumber = this?.contactNumber.orEmpty(),
        deliveryAddress = this?.deliveryAddress.orEmpty(),
        latitude = this?.latitude.orZero(),
        longitude = this?.longitude.orZero(),
        orderItems = this?.orderItems?.map { it.toOrderMenuItem() }.orEmpty(),
        totalPrice = this?.totalPrice.orZero(),
        currency = this?.currency.orEmpty(),
        status = OrderStatus.fromValueOrDefault(this?.status),
        statusText = this?.statusText.orEmpty(),
        orderCreatedTime = this?.orderCreatedTime.orZero(),
        statusChangedTime = this?.statusChangedTime.orZero(),
        note = this?.note.orEmpty(),
        content = this?.orderItems?.joinToString(", ") { item ->
            "${if (item.quantity.orZero() > 1) "${item.quantity} x " else ""}${item.name}"
        }.orEmpty()
    )
}
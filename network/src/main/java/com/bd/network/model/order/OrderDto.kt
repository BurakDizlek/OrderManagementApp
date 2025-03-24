package com.bd.network.model.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String?,
    val contactName: String?,
    val contactNumber: String?,
    val deliveryAddress: String?,
    val latitude: Double?,
    val longitude: Double?,
    val orderItems: List<OrderItem>?,
    val totalPrice: Double?,
    val currency: String?,
    val status: String?,
    val statusText: String?,
    val orderCreatedTime: Long?,
    val statusChangedTime: Long?,
    val note: String? = null
)

@Serializable
data class OrderItem(
    val id: Int?,
    val name: String?,
    val price: Double?,
    val currency: String?,
)
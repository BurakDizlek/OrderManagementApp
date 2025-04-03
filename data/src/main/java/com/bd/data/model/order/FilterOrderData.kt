package com.bd.data.model.order

data class FilterOrderData(
    val query: String? = null,
    val fromTime: Long? = null,
    val toTime: Long? = null,
    val statuses: List<OrderStatus>? = null
)
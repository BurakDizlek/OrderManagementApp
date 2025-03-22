package com.bd.data.model.order

data class FilterOrderData(
    val query: String?,
    val fromTime: Long?,
    val toTime: Long?,
    val statuses: List<OrderStatus>?
)
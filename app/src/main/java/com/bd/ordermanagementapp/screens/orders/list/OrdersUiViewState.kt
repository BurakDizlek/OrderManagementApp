package com.bd.ordermanagementapp.screens.orders.list

import com.bd.data.model.order.FilterOrderData
import com.bd.data.model.order.Order
import com.bd.data.model.order.OrderStatus

data class OrdersUiViewState(
    val loading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val isOrdersEmpty: Boolean = false,
    val errorMessage: String? = null,

    // filterData
    val filterData: FilterOrderData = FilterOrderData(
        query = null,
        fromTime = System.currentTimeMillis(),
        toTime = null,
        statuses = listOf(OrderStatus.OPEN)
    )
)
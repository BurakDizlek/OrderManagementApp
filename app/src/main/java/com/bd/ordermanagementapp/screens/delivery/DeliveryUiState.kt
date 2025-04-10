package com.bd.ordermanagementapp.screens.delivery

import com.bd.data.extensions.ensureEndOfDay
import com.bd.data.extensions.ensureStartOfDay
import com.bd.data.model.order.FilterOrderData
import com.bd.data.model.order.Order
import com.bd.data.model.order.OrderStatus

data class DeliveryUiState(
    val loading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val errorMessage: String? = null,
    val filterData: FilterOrderData = FilterOrderData(
        query = null,
        fromTime = System.currentTimeMillis().ensureStartOfDay(),
        toTime = System.currentTimeMillis().ensureEndOfDay(),
        statuses = listOf(
            OrderStatus.OPEN,
            OrderStatus.ON_THE_WAY,
            OrderStatus.CANCELED,
            OrderStatus.COMPLETED,
        )
    ),
)
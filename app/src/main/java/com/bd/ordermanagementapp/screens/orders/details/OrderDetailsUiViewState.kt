package com.bd.ordermanagementapp.screens.orders.details

import com.bd.data.model.order.Order

data class OrderDetailsUiViewState(
    val loading: Boolean = false,
    val order: Order? = null,
    val errorMessage: String? = null,
)
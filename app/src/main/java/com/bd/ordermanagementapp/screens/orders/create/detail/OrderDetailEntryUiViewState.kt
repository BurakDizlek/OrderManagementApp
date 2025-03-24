package com.bd.ordermanagementapp.screens.orders.create.detail

import com.bd.data.model.order.Order

data class OrderDetailEntryUiViewState(
    val loading: Boolean = false,
    val createdOrder: Order? = null,
    val errorMessage: String? = null,
)
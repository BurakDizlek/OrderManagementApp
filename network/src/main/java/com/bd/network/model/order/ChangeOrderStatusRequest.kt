package com.bd.network.model.order

import kotlinx.serialization.Serializable

@Serializable
data class ChangeOrderStatusRequest(val orderId: String)

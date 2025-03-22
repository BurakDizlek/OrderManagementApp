package com.bd.network.service.order

import com.bd.network.model.BaseResponse
import com.bd.network.model.order.CreateOrderRequest
import com.bd.network.model.order.OrderDto


interface OrderService {

    suspend fun getAllOrders(): BaseResponse<List<OrderDto>?>

    suspend fun createOrder(request: CreateOrderRequest): BaseResponse<OrderDto?>

    suspend fun getFilteredOrders(
        query: String?,
        fromTime: Long?,
        toTime: Long?,
        statuses: List<String>?
    ): BaseResponse<List<OrderDto>?>

    suspend fun cancelOrRejectOrder(orderId: String): BaseResponse<OrderDto?>

    suspend fun startDelivery(orderId: String): BaseResponse<OrderDto?>

    suspend fun completeDelivery(orderId: String): BaseResponse<OrderDto?>
}
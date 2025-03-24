package com.bd.data.repository.order

import com.bd.data.model.BaseResult
import com.bd.data.model.order.CreateOrderData
import com.bd.data.model.order.FilterOrderData
import com.bd.data.model.order.Order

interface OrderRepository {

    suspend fun getAllOrders(): BaseResult<List<Order>>

    suspend fun createOrder(createOrderData: CreateOrderData): BaseResult<Order>

    suspend fun getFilteredOrders(filterOrderData: FilterOrderData): BaseResult<List<Order>>

    suspend fun cancelOrder(orderId: String): BaseResult<Order>

    suspend fun rejectOrder(orderId: String): BaseResult<Order>

    suspend fun startDelivery(orderId: String): BaseResult<Order>

    suspend fun completeDelivery(orderId: String): BaseResult<Order>

}
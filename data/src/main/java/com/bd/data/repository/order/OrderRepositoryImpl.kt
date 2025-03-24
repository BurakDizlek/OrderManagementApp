package com.bd.data.repository.order

import com.bd.data.extensions.toBaseResult
import com.bd.data.model.BaseResult
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.data.model.order.CreateOrderData
import com.bd.data.model.order.FilterOrderData
import com.bd.data.model.order.Order
import com.bd.data.model.order.toOrder
import com.bd.network.model.order.CreateOrderRequest
import com.bd.network.service.order.OrderService

class OrderRepositoryImpl(private val service: OrderService) : OrderRepository {

    override suspend fun getAllOrders(): BaseResult<List<Order>> {
        val response = service.getAllOrders()
        return BaseResult(
            data = response.data?.map { it.toOrder() }.orEmpty(),
            message = response.message.orEmpty(),
            code = response.code ?: NO_CODE
        )
    }

    override suspend fun createOrder(createOrderData: CreateOrderData): BaseResult<Order> {
        val response = service.createOrder(
            request = CreateOrderRequest(
                isQuickOrder = createOrderData.isQuickOrder,
                menuItemId = createOrderData.menuItemId,
                latitude = createOrderData.latitude,
                longitude = createOrderData.longitude,
                address = createOrderData.address,
                note = createOrderData.note
            )
        )

        return response.toBaseResult { it.toOrder() }
    }

    override suspend fun getFilteredOrders(filterOrderData: FilterOrderData): BaseResult<List<Order>> {
        val response = service.getFilteredOrders(
            query = filterOrderData.query,
            fromTime = filterOrderData.fromTime,
            toTime = filterOrderData.toTime,
            statuses = filterOrderData.statuses?.map { it.name }
        )

        return response.toBaseResult { list -> list?.map { it.toOrder() }.orEmpty() }
    }

    override suspend fun cancelOrder(orderId: String): BaseResult<Order> {
        val response = service.cancelOrRejectOrder(orderId = orderId)
        return response.toBaseResult { it.toOrder() }
    }

    override suspend fun rejectOrder(orderId: String): BaseResult<Order> {
        val response = service.cancelOrRejectOrder(orderId = orderId)
        return response.toBaseResult { it.toOrder() }
    }

    override suspend fun startDelivery(orderId: String): BaseResult<Order> {
        val response = service.startDelivery(orderId = orderId)
        return response.toBaseResult { it.toOrder() }
    }

    override suspend fun completeDelivery(orderId: String): BaseResult<Order> {
        val response = service.completeDelivery(orderId = orderId)
        return response.toBaseResult { it.toOrder() }
    }
}
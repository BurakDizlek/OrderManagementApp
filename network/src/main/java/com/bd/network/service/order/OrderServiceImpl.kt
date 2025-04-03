package com.bd.network.service.order

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.order.ChangeOrderStatusRequest
import com.bd.network.model.order.CreateOrderRequest
import com.bd.network.model.order.OrderDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val ORDER_URL = "$BASE_URL/orders"

class OrderServiceImpl(private val client: HttpClient) : OrderService {
    override suspend fun getAllOrders(): BaseResponse<List<OrderDto>?> {
        return handleErrors {
            client.get(ORDER_URL) {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun getOrderById(orderId: String): BaseResponse<OrderDto?> {
        return handleErrors {
            client.get("$ORDER_URL/byId") {
                url {
                    parameters.append("orderId", orderId)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun createOrder(request: CreateOrderRequest): BaseResponse<OrderDto?> {
        return handleErrors {
            client.post("$ORDER_URL/create") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    override suspend fun getFilteredOrders(
        query: String?,
        fromTime: Long?,
        toTime: Long?,
        statuses: List<String>?
    ): BaseResponse<List<OrderDto>?> {
        return handleErrors {
            client.get("$ORDER_URL/filter") {
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("query", query.orEmpty())
                    fromTime?.let {
                        parameters.append("fromTime", it.toString())
                    }
                    toTime?.let {
                        parameters.append("toTime", it.toString())
                    }
                    if (statuses != null && statuses.isNotEmpty()) {
                        parameters.append("statuses", statuses.joinToString(","))
                    }
                }
            }
        }
    }

    override suspend fun cancelOrRejectOrder(orderId: String): BaseResponse<OrderDto?> {
        return handleErrors {
            client.post("$ORDER_URL/cancel") {
                contentType(ContentType.Application.Json)
                setBody(ChangeOrderStatusRequest(orderId = orderId))
            }
        }
    }

    override suspend fun startDelivery(orderId: String): BaseResponse<OrderDto?> {
        return handleErrors {
            client.post("$ORDER_URL/start-delivery") {
                contentType(ContentType.Application.Json)
                setBody(ChangeOrderStatusRequest(orderId = orderId))
            }
        }
    }

    override suspend fun completeDelivery(orderId: String): BaseResponse<OrderDto?> {
        return handleErrors {
            client.post("$ORDER_URL/complete-delivery") {
                contentType(ContentType.Application.Json)
                setBody(ChangeOrderStatusRequest(orderId = orderId))
            }
        }
    }
}
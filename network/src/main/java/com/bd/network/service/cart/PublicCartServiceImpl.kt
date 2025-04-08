package com.bd.network.service.cart

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.cart.AddOrDeletePublicCartRequest
import com.bd.network.model.cart.CartResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val PUBLIC_CART_URL = "$BASE_URL/public-cart"

class PublicCartServiceImpl(private val client: HttpClient) : PublicCartService {
    override suspend fun getCart(deviceId: String): BaseResponse<CartResponse?> {
        return handleErrors {
            client.get(PUBLIC_CART_URL) {
                url {
                    parameters.append("deviceId", deviceId)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun addToCart(deviceId: String, menuItemId: Int): BaseResponse<CartResponse?> {
        return handleErrors {
            client.post(PUBLIC_CART_URL) {
                contentType(ContentType.Application.Json)
                setBody(AddOrDeletePublicCartRequest(deviceId = deviceId, menuItemId = menuItemId))
            }
        }
    }

    override suspend fun deleteFromCart(
        deviceId: String,
        menuItemId: Int,
    ): BaseResponse<CartResponse?> {
        return handleErrors {
            client.delete(PUBLIC_CART_URL) {
                contentType(ContentType.Application.Json)
                setBody(AddOrDeletePublicCartRequest(deviceId = deviceId, menuItemId = menuItemId))
            }
        }
    }
}
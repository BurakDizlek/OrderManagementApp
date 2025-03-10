package com.bd.network.service.cart

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.cart.AddOrDeleteCartRequest
import com.bd.network.model.cart.CartResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val CART_URL = "$BASE_URL/cart"

class CartServiceImpl(private val client: HttpClient) : CartService {
    override suspend fun getCart(): BaseResponse<CartResponse?> {
        return handleErrors {
            client.get(CART_URL) {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun addToCart(menuItemId: Int): BaseResponse<CartResponse?> {
        return handleErrors {
            client.post(CART_URL) {
                contentType(ContentType.Application.Json)
                setBody(AddOrDeleteCartRequest(menuItemId = menuItemId))
            }
        }
    }

    override suspend fun deleteFromCart(menuItemId: Int): BaseResponse<CartResponse?> {
        return handleErrors {
            client.delete(CART_URL) {
                contentType(ContentType.Application.Json)
                setBody(AddOrDeleteCartRequest(menuItemId = menuItemId))
            }
        }
    }
}
package com.bd.data.repository.cart

import com.bd.core.session.SessionManager
import com.bd.data.model.BaseResult
import com.bd.data.model.Cart
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.data.model.toCart
import com.bd.network.model.BaseResponse
import com.bd.network.model.cart.CartResponse
import com.bd.network.service.cart.CartService
import com.bd.network.service.cart.PublicCartService

class CartRepositoryImpl(
    private val service: CartService,
    private val publicCartService: PublicCartService,
    private val sessionManager: SessionManager,
) : CartRepository {

    override suspend fun getCart(): BaseResult<Cart?> {
        val response = if (sessionManager.isUserLoggedIn()) {
            service.getCart()
        } else {
            publicCartService.getCart(deviceId = sessionManager.getDeviceId())
        }
        return createResultByResponse(response = response)
    }

    override suspend fun addToCart(menuItemId: Int): BaseResult<Cart?> {
        val response = if (sessionManager.isUserLoggedIn()) {
            service.addToCart(menuItemId = menuItemId)
        } else {
            publicCartService.addToCart(
                deviceId = sessionManager.getDeviceId(),
                menuItemId = menuItemId
            )
        }
        return createResultByResponse(response = response)
    }

    override suspend fun deleteFromCart(menuItemId: Int): BaseResult<Cart?> {
        val response = if (sessionManager.isUserLoggedIn()) {
            service.deleteFromCart(menuItemId = menuItemId)
        } else {
            publicCartService.deleteFromCart(
                deviceId = sessionManager.getDeviceId(),
                menuItemId = menuItemId
            )
        }
        return createResultByResponse(response = response)
    }

    override suspend fun mergeCarts(): BaseResult<Cart?> {
        val response = service.mergeCarts()
        return createResultByResponse(response = response)
    }

    private fun createResultByResponse(response: BaseResponse<CartResponse?>): BaseResult<Cart?> {
        val result = BaseResult(
            data = response.data?.toCart(),
            message = response.message.orEmpty(),
            code = response.code ?: NO_CODE
        )
        return result
    }
}
package com.bd.data.repository.cart

import com.bd.data.model.BaseResult
import com.bd.data.model.Cart
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.data.model.orEmpty
import com.bd.data.model.toCart
import com.bd.network.model.BaseResponse
import com.bd.network.model.cart.CartResponse
import com.bd.network.service.cart.CartService

class CartRepositoryImpl(private val service: CartService) : CartRepository {

    override suspend fun getCart(): BaseResult<Cart> {
        val response = service.getCart()
        return createResultByResponse(response = response)
    }

    override suspend fun addToCart(menuItemId: Int): BaseResult<Cart> {
        val response = service.addToCart(menuItemId = menuItemId)

        return createResultByResponse(response = response)
    }

    override suspend fun deleteFromCart(menuItemId: Int): BaseResult<Cart> {
        val response = service.deleteFromCart(menuItemId = menuItemId)

        return createResultByResponse(response = response)
    }


    private fun createResultByResponse(response: BaseResponse<CartResponse?>): BaseResult<Cart> {
        val result = BaseResult(
            data = response.data?.toCart().orEmpty(),
            message = response.message.orEmpty(),
            code = response.code ?: NO_CODE
        )
        return result
    }
}
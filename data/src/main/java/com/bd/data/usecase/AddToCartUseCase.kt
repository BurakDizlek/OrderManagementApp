package com.bd.data.usecase

import com.bd.data.model.BaseResult
import com.bd.data.model.Cart
import com.bd.data.model.ResultCodes
import com.bd.data.repository.cart.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(menuItemId: Int): BaseResult<Cart?> {
        return try {
            cartRepository.addToCart(menuItemId = menuItemId)
        } catch (e: Exception) {
            BaseResult(
                data = null,
                message = e.localizedMessage.orEmpty(),
                code = ResultCodes.FAILURE
            )
        }
    }
}
package com.bd.network.service.cart

import com.bd.network.model.BaseResponse
import com.bd.network.model.cart.CartResponse


interface CartService {
    suspend fun getCart(): BaseResponse<CartResponse?>

    suspend fun addToCart(menuItemId: Int): BaseResponse<CartResponse?>

    suspend fun deleteFromCart(menuItemId: Int): BaseResponse<CartResponse?>
}
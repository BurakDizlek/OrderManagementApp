package com.bd.network.service.cart

import com.bd.network.model.BaseResponse
import com.bd.network.model.cart.CartResponse

interface PublicCartService {
    suspend fun getCart(deviceId: String): BaseResponse<CartResponse?>

    suspend fun addToCart(deviceId: String, menuItemId: Int): BaseResponse<CartResponse?>

    suspend fun deleteFromCart(deviceId: String, menuItemId: Int): BaseResponse<CartResponse?>
}
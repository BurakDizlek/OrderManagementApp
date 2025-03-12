package com.bd.data.repository.cart

import com.bd.data.model.BaseResult
import com.bd.data.model.Cart

interface CartRepository {

    suspend fun getCart(): BaseResult<Cart?>

    suspend fun addToCart(menuItemId: Int): BaseResult<Cart?>

    suspend fun deleteFromCart(menuItemId: Int): BaseResult<Cart?>
}
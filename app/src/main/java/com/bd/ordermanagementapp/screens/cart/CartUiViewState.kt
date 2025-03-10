package com.bd.ordermanagementapp.screens.cart

import com.bd.data.model.Cart

data class CartUiViewState(
    val cart: Cart? = null,
    val loadingCart: Boolean = false,
    val errorCart: String? = null,
    val errorAddToCartMessage: String? = null,
    val errorDeleteFromCartMessage: String? = null,
)
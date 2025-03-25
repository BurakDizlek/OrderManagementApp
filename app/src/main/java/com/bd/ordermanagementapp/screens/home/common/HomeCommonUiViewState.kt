package com.bd.ordermanagementapp.screens.home.common

import com.bd.data.model.Cart

data class HomeCommonUiViewState(
    //Add to cart
    val cart: Cart? = null,
    val loadingAddToCart: Boolean = false,
    val errorAddToCart: String? = null,
    val orderOrCartDecisionMenuItemId: Int? = null,

    //Quick order
    val displayNeedLoginDialog: Boolean = false,
    val quickOrderMenuItemId: Int? = null
)
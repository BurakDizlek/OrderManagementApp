package com.bd.ordermanagementapp.screens.home

import com.bd.data.model.Campaign
import com.bd.data.model.Cart
import com.bd.data.model.MenuItem

data class HomeUiViewState(
    val campaigns: List<Campaign> = listOf(),
    val loadingCampaigns: Boolean = false,
    val errorCampaigns: String? = null,
    //Menu items
    val currentPage: Int = 1,
    val pageSize: Int = 5,
    val menuItems: List<MenuItem> = listOf(),
    val loadingMenuItems: Boolean = false,
    val hasMoreMenuItems: Boolean = true,
    val errorMenuItems: String? = null,
    //Add to cart
    val cart: Cart? = null,
    val loadingOrderOrCart: Boolean = false,
    val errorOrderOrCart: String? = null,
    val orderOrCartDecisionMenuItemId: Int? = null,

    //Quick order
    val displayNeedLoginDialog: Boolean = false,
    val quickOrderMenuItemId: Int? = null
)
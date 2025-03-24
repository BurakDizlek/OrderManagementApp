package com.bd.ordermanagementapp.screens.home.campaign

import com.bd.data.model.MenuItem

data class CampaignDetailsUiViewState(
    val menuItems: List<MenuItem> = listOf(),
    val loadingMenuItems: Boolean = false,
    val errorMenuItems: String? = null,
    val showDataNullError: Boolean = false
)
package com.bd.ordermanagementapp.screens.home

import com.bd.data.model.Campaign

data class HomeUiViewState(
    val campaigns: List<Campaign> = listOf(),
    val loadingCampaigns: Boolean = false,
    val errorCampaigns: String? = null,
)
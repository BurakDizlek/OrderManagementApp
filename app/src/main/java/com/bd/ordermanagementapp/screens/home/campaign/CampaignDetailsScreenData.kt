package com.bd.ordermanagementapp.screens.home.campaign

import kotlinx.serialization.Serializable

@Serializable
data class CampaignDetailsScreenData(
    val name: String,
    val description: String,
    val image: String,
    val menuItemIds: String
)
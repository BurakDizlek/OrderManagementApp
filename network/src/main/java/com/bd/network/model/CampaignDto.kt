package com.bd.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CampaignDto(
    val id: Int?,
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    val menuItemIds: String?
)
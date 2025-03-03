package com.bd.data.repository

import com.bd.data.model.Campaign
import com.bd.data.concatenateUrl
import com.bd.network.HttpRoutes
import com.bd.network.service.campaign.CampaignService

class CampaignRepositoryImpl(private val campaignService: CampaignService) : CampaignRepository {
    override suspend fun getAllCampaigns(): List<Campaign> {
        return campaignService.getCampaigns().data?.map {
            Campaign(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                imageUrl = concatenateUrl(base = HttpRoutes.BASE_URL, path = it.imageUrl.orEmpty()),
                menuItemIds = it.menuItemIds.orEmpty()
            )
        }.orEmpty()
    }
}
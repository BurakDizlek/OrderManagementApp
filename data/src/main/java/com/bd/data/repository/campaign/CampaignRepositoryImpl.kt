package com.bd.data.repository.campaign

import com.bd.data.concatenateUrl
import com.bd.data.model.BaseResult
import com.bd.data.model.Campaign
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.network.NetworkConstants
import com.bd.network.service.campaign.CampaignService

class CampaignRepositoryImpl(private val campaignService: CampaignService) : CampaignRepository {
    override suspend fun getAllCampaigns(): BaseResult<List<Campaign>> {
        val response = campaignService.getCampaigns()
        val data = response.data?.map {
            Campaign(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                imageUrl = concatenateUrl(
                    base = NetworkConstants.BASE_URL, path = it.imageUrl.orEmpty()
                ),
                menuItemIds = it.menuItemIds.orEmpty()
            )
        }.orEmpty()

        return BaseResult(
            data = data, message = response.message.orEmpty(), code = response.code ?: NO_CODE
        )
    }
}
package com.bd.network.service.campaign

import com.bd.network.model.BaseResponse
import com.bd.network.model.CampaignDto

interface CampaignService {
    suspend fun getCampaigns(): BaseResponse<List<CampaignDto>>
}
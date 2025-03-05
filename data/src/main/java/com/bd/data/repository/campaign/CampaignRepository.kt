package com.bd.data.repository.campaign

import com.bd.data.model.BaseResult
import com.bd.data.model.Campaign

interface CampaignRepository {
    suspend fun getAllCampaigns(): BaseResult<List<Campaign>>
}
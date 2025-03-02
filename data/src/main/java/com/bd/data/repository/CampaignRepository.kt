package com.bd.data.repository

import com.bd.data.model.Campaign

interface CampaignRepository {
    suspend fun getAllCampaigns(): List<Campaign>
}
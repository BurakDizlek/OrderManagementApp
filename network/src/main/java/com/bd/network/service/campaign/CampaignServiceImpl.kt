package com.bd.network.service.campaign

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.CampaignDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val CAMPAIGNS_URL = "$BASE_URL/campaigns"

class CampaignServiceImpl(private val client: HttpClient) : CampaignService {
    override suspend fun getCampaigns(): BaseResponse<List<CampaignDto>> {
        return handleErrors {
            client.get(CAMPAIGNS_URL) {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
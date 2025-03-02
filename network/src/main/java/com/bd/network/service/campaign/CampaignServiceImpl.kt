package com.bd.network.service.campaign

import com.bd.network.HttpRoutes
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.CampaignDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CampaignServiceImpl(private val client: HttpClient) : CampaignService {
    override suspend fun getCampaigns(): BaseResponse<List<CampaignDto>> {
        return handleErrors {
            client.get(HttpRoutes.CAMPAIGNS) {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
package com.bd.network.service.geocoding

import com.bd.network.NetworkConstants.mapsKey
import com.bd.network.errors.handleErrors
import com.bd.network.model.geocoding.GeocodingResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json"

class GeocodingServiceImpl(private val client: HttpClient) : GeocodingService {
    override suspend fun getAddress(
        latitude: Double,
        longitude: Double,
    ): GeocodingResponse? {
        return handleErrors {
            client.get(GEOCODING_URL) {
                url {
                    parameters.append("latlng", "$latitude,$longitude")
                    parameters.append("key", mapsKey)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }
}
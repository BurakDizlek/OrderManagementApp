package com.bd.network.service.geocoding

import com.bd.network.model.geocoding.GeocodingResponse

interface GeocodingService {
    suspend fun getAddress(latitude: Double, longitude: Double): GeocodingResponse?
}
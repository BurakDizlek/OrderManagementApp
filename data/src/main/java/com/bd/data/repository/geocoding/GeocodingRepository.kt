package com.bd.data.repository.geocoding

import com.bd.data.model.BaseResult

interface GeocodingRepository {
    suspend fun getAddress(latitude: Double, longitude: Double): BaseResult<String>?
}
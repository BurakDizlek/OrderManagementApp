package com.bd.data.repository.geocoding

import com.bd.data.model.BaseResult
import com.bd.data.model.ResultCodes
import com.bd.network.service.geocoding.GeocodingService

class GeocodingRepositoryImpl(private val service: GeocodingService) : GeocodingRepository {
    override suspend fun getAddress(
        latitude: Double,
        longitude: Double,
    ): BaseResult<String>? {
        val response = service.getAddress(latitude = latitude, longitude = longitude)

        return BaseResult(
            data = response?.results?.firstOrNull()?.formattedAddress.orEmpty(),
            message = "",
            code = ResultCodes.SUCCESS
        )
    }
}
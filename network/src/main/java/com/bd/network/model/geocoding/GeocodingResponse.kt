package com.bd.network.model.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(
    val results: List<GeocodingResult>?,
    val status: String?,
)

@Serializable
data class GeocodingResult(
    @SerialName("formatted_address") val formattedAddress: String?,
)
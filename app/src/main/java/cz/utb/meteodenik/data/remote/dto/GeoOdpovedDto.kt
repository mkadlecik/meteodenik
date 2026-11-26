package cz.utb.meteodenik.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeoOdpovedDto(
    val results: List<GeoMistoDto> = emptyList()   // klíč CHYBÍ, když se nic nenajde
)

@Serializable
data class GeoMistoDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    val admin1: String? = null,        // kraj / region
    val timezone: String? = null
)
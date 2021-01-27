package com.trelp.aag2020.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("id") val id: Int,
    @SerialName("cast") val cast: List<Cast>
)
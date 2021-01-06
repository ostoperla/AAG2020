package com.trelp.aag2020.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreX(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)
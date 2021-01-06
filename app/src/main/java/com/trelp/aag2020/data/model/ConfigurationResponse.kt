package com.trelp.aag2020.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images") val images: Images,
    @SerialName("change_keys") val changeKeys: List<String>
)
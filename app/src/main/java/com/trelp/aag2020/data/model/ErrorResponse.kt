package com.trelp.aag2020.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("status_message") val statusMessage: String,
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int
)
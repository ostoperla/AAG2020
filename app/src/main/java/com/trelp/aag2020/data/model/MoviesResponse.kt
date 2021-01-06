package com.trelp.aag2020.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
//    @SerialName("dates") val dates: Dates,
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<Result>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
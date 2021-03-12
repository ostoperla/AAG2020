package com.trelp.aag2020.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDto(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)
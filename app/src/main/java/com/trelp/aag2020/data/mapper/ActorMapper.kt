package com.trelp.aag2020.data.mapper

import com.trelp.aag2020.data.model.CastDto
import com.trelp.aag2020.domain.entity.Actor
import javax.inject.Inject

class ActorMapper @Inject constructor(
    private val imageUrlMapper: ImageUrlMapper
) {

    fun map(cast: CastDto): Actor {
        return Actor(
            id = cast.id,
            name = cast.name,
            profilePath = imageUrlMapper.map(IMAGE_SIZE, cast.profilePath)
        )
    }

    companion object {
        private const val IMAGE_SIZE = "w342"
    }
}
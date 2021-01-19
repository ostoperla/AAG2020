package com.trelp.aag2020.data.mapper

import com.trelp.aag2020.data.model.Genre
import javax.inject.Inject

class GenreMapper @Inject constructor() {

    fun map(genre: Genre): com.trelp.aag2020.domain.entity.Genre {
        return com.trelp.aag2020.domain.entity.Genre(
            id = genre.id,
            name = genre.name
        )
    }
}
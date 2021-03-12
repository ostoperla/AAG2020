package com.trelp.aag2020.data.mapper

import com.trelp.aag2020.data.model.GenreDto
import com.trelp.aag2020.domain.entity.Genre
import javax.inject.Inject

class GenreMapper @Inject constructor() {

    fun map(genre: GenreDto): Genre {
        return Genre(
            id = genre.id,
            name = genre.name
        )
    }
}
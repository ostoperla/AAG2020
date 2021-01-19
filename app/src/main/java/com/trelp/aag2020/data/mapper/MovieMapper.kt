package com.trelp.aag2020.data.mapper

import com.trelp.aag2020.data.model.Movie
import com.trelp.aag2020.domain.entity.Genre
import javax.inject.Inject
import kotlin.random.Random

class MovieMapper @Inject constructor(
    private val imageUrlMapper: ImageUrlMapper
) {
    fun map(
        movie: Movie,
        genresMap: Map<Int, Genre>
    ): com.trelp.aag2020.domain.entity.Movie {
        return com.trelp.aag2020.domain.entity.Movie(
            id = movie.id,
            minimumAge = if (movie.adult) 16 else 13,
            posterPath = imageUrlMapper.map(IMAGE_SIZE, movie.posterPath),
            genres = movie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            title = movie.originalTitle,
            runtime = Random.nextInt(61, 197)
        )
    }

    companion object {
        private const val IMAGE_SIZE = "w342"
    }
}
package com.trelp.aag2020.data.mapper

import com.trelp.aag2020.data.model.MovieDto
import com.trelp.aag2020.domain.entity.Genre
import com.trelp.aag2020.domain.entity.Movie
import javax.inject.Inject
import kotlin.random.Random

class MovieMapper @Inject constructor(
    private val imageUrlMapper: ImageUrlMapper
) {
    fun map(
        movie: MovieDto,
        genresMap: Map<Int, Genre>
    ): Movie {
        return Movie(
            id = movie.id,
            minimumAge = if (movie.adult) AGE_16 else AGE_13,
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
        private const val AGE_16 = 16
        private const val AGE_13 = 13
    }
}
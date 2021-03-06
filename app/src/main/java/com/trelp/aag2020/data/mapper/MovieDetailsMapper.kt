package com.trelp.aag2020.data.mapper

import com.trelp.aag2020.data.model.MovieDetailsResponse
import com.trelp.aag2020.domain.entity.MovieDetails
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(
    private val imageUrlMapper: ImageUrlMapper,
    private val genreMapper: GenreMapper
) {
    fun map(movie: MovieDetailsResponse): MovieDetails {
        return MovieDetails(
            id = movie.id,
            minimumAge = if (movie.adult) AGE_16 else AGE_13,
            backdropPath = imageUrlMapper.map(IMAGE_SIZE, movie.backdropPath),
            genres = movie.genres.map {
                genreMapper.map(it)
            },
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            title = movie.originalTitle,
            overview = movie.overview
        )
    }

    companion object {
        private const val IMAGE_SIZE = "w342"
        private const val AGE_16 = 16
        private const val AGE_13 = 13
    }
}
package com.trelp.aag2020.domain.interactor

import com.trelp.aag2020.domain.repository.MovieRepository
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.domain.entity.MovieFilter
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovies(
        filter: MovieFilter = MovieFilter.TOP_RATED
    ): List<Movie> {
        return movieRepository.getMovies(filter)
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return movieRepository.getMovieDetails(movieId)
    }
}
package com.trelp.aag2020.domain

import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.domain.entity.MovieFilter

interface MovieRepository {
    suspend fun getMovies(filter: MovieFilter): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getActors(movieId: Int): List<Actor>
}
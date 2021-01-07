package com.trelp.aag2020.domain

import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Genre
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.entity.MovieDetails

interface MovieRepository {
    suspend fun getNowPlayingMoviesList(): List<Movie>
    suspend fun getPopularMoviesList(): List<Movie>
    suspend fun getTopRatedMoviesList(): List<Movie>
    suspend fun getUpcomingMoviesList(): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getActorsList(movieId: Int): List<Actor>
    suspend fun getGenresList(): List<Genre>
}
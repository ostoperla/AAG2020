package com.trelp.aag2020.data.network

import com.trelp.aag2020.data.model.CastResponse
import com.trelp.aag2020.data.model.ConfigurationResponse
import com.trelp.aag2020.data.model.GenresResponse
import com.trelp.aag2020.data.model.MovieDetailsResponse
import com.trelp.aag2020.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbAPI {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopular(): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CastResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse
}
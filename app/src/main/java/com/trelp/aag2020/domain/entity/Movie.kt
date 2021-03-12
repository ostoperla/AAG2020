package com.trelp.aag2020.domain.entity

data class Movie(
    val id: Int,
    val minimumAge: Int,
    val posterPath: String?,
    val genres: List<Genre>,
    val voteAverage: Float,
    val voteCount: Int,
    val title: String,
    val runtime: Int
)

fun Movie.isSame(other: Movie) = id == other.id

data class MovieDetails(
    val id: Int,
    val minimumAge: Int,
    val backdropPath: String?,
    val genres: List<Genre>,
    val voteAverage: Float,
    val voteCount: Int,
    val title: String,
    val overview: String
)
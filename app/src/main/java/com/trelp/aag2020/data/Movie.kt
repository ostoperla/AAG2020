package com.trelp.aag2020.data

import androidx.annotation.DrawableRes

data class Movie(
    val id: Int,
    @DrawableRes val posterSmall: Int,
    @DrawableRes val poster: Int,
    val ageLimit: String,
    val title: String,
    val tags: String,
    val rating: Float,
    val reviewCount: Int,
    val isLike: Boolean,
    val duration: Int,
    val overview: String,
    val actors: List<Actor>
)

data class MovieDetails(
    val id: Int,
    @DrawableRes val poster: Int,
    val ageLimit: String,
    val title: String,
    val tags: String,
    val rating: Float,
    val reviewCount: Int,
    val overview: String,
    val actors: List<Actor>
)

fun Movie.isSame(other: Movie) = id == other.id
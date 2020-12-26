package com.trelp.aag2020.data

import com.trelp.aag2020.data.storage.LocalDataSource
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Genre
import com.trelp.aag2020.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.system.measureTimeMillis
import com.trelp.aag2020.data.model.Actor as ResponseActor
import com.trelp.aag2020.data.model.Genre as ResponseGenre
import com.trelp.aag2020.data.model.Movie as ResponseMovie

class MoviesRepository(
    private val localDataSource: LocalDataSource
) {

    suspend fun loadMovies() = withContext(Dispatchers.Default) {
        val movies: List<Movie>

        val time = measureTimeMillis {

            val actorsMap = localDataSource.loadData<ResponseActor>("people.json")
                .map {
                    Actor(
                        id = it.id,
                        name = it.name,
                        picture = it.profilePicture
                    )
                }
                .associateBy { it.id }

            val genresMap = localDataSource.loadData<ResponseGenre>("genres.json")
                .map {
                    Genre(
                        id = it.id,
                        name = it.name
                    )
                }
                .associateBy { it.id }

            movies = localDataSource.loadData<ResponseMovie>("data.json")
                .map { jsonMovie ->
                    Movie(
                        id = jsonMovie.id,
                        title = jsonMovie.title,
                        overview = jsonMovie.overview,
                        poster = jsonMovie.posterPicture,
                        backdrop = jsonMovie.backdropPicture,
                        ratings = jsonMovie.ratings,
                        numberOfRatings = jsonMovie.votesCount,
                        minimumAge = if (jsonMovie.adult) 16 else 13,
                        runtime = jsonMovie.runtime,
                        genres = jsonMovie.genreIds.map {
                            genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                        },
                        actors = jsonMovie.actors.map {
                            actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
                        }
                    )
                }
        }

        Timber.d("$time ms")

        movies
    }
}
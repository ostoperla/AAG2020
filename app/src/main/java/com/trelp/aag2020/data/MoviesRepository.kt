package com.trelp.aag2020.data

import com.trelp.aag2020.data.model.ActorDto
import com.trelp.aag2020.data.model.GenreDto
import com.trelp.aag2020.data.model.MovieDto
import com.trelp.aag2020.data.storage.LocalDataSource
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Genre
import com.trelp.aag2020.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) {

    suspend fun loadMovies() = withContext(Dispatchers.Default) {
        val movies: List<Movie>

        val time = measureTimeMillis {

            val actorsMap = localDataSource.loadData<ActorDto>("people.json")
                .map {
                    Actor(
                        id = it.id,
                        name = it.name,
                        picture = it.profilePicture
                    )
                }
                .associateBy { it.id }

            val genresMap = localDataSource.loadData<GenreDto>("genres.json")
                .map {
                    Genre(
                        id = it.id,
                        name = it.name
                    )
                }
                .associateBy { it.id }

            movies = localDataSource.loadData<MovieDto>("data.json")
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
                        genres = jsonMovie.genreIds.mapNotNull {
                            genresMap[it]
                        },
                        actors = jsonMovie.actors.mapNotNull {
                            actorsMap[it]
                        }
                    )
                }
        }

        Timber.d("$time ms")

        movies
    }

    suspend fun loadMovie(movieId: Int) =
        loadMovies().find { it.id == movieId }
}
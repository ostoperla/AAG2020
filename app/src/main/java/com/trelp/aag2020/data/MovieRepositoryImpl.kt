package com.trelp.aag2020.data

import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Genre
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.entity.MovieDetails
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbAPI,
    private val dispatchers: DispatchersProvider
) : MovieRepository {

    override suspend fun getNowPlayingMoviesList(): List<Movie> = withContext(dispatchers.io()) {
        val genresMap = getGenresList().associateBy { it.id }

        api.getNowPlaying().results
            .map {
                convertToMovieEntity(it, genresMap)
            }
    }

    override suspend fun getPopularMoviesList(): List<Movie> = withContext(dispatchers.io()) {
        val genresMap = getGenresList().associateBy { it.id }

        api.getPopular().results
            .map {
                convertToMovieEntity(it, genresMap)
            }
    }

    override suspend fun getTopRatedMoviesList(): List<Movie> = withContext(dispatchers.io()) {
        val genresMap = getGenresList().associateBy { it.id }

        api.getTopRated().results
            .map {
                convertToMovieEntity(it, genresMap)
            }
    }

    override suspend fun getUpcomingMoviesList(): List<Movie> = withContext(dispatchers.io()) {
        val genresMap = getGenresList().associateBy { it.id }

        api.getUpcoming().results
            .map {
                convertToMovieEntity(it, genresMap)
            }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails =
        withContext(dispatchers.io()) {
            val movie = api.getDetails(movieId)

            MovieDetails(
                id = movie.id,
                minimumAge = if (movie.adult) 16 else 13,
                backdropPath = createAbsoluteUrl(movie.backdropPath),
                genres = movie.genres.map {
                    Genre(
                        id = it.id,
                        name = it.name
                    )
                },
                voteAverage = movie.voteAverage.toFloat(),
                voteCount = movie.voteCount,
                title = movie.originalTitle,
                overview = movie.overview
            )
        }

    override suspend fun getActorsList(movieId: Int): List<Actor> = withContext(dispatchers.io()) {
        api.getMovieCredits(movieId).cast
            .map {
                Actor(
                    id = it.id,
                    name = it.name,
                    profilePath = createAbsoluteUrl(it.profilePath)
                )
            }
    }

    override suspend fun getGenresList(): List<Genre> = withContext(dispatchers.io()) {
        api.getGenresForMovies().genres
            .map {
                Genre(
                    id = it.id,
                    name = it.name
                )
            }
    }

    private fun convertToMovieEntity(
        movie: com.trelp.aag2020.data.model.Movie,
        genresMap: Map<Int, Genre>
    ): Movie {
        return Movie(
            id = movie.id,
            minimumAge = if (movie.adult) 16 else 13,
            posterPath = createAbsoluteUrl(movie.posterPath),
            genres = movie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            voteAverage = movie.voteAverage.toFloat(),
            voteCount = movie.voteCount,
            title = movie.originalTitle,
            runtime = 777
        )
    }

    private fun createAbsoluteUrl(relativeUrl: String?) =
        relativeUrl?.let { "$SECURE_BASE_URL$IMAGE_SIZE$it" } ?: ""

    companion object {
        private const val SECURE_BASE_URL = "https://image.tmdb.org/t/p/"
        private const val IMAGE_SIZE = "w342"
    }
}
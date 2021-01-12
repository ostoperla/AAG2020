package com.trelp.aag2020.data

import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.data.storage.PrefsManager
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Genre
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.domain.entity.MovieFilter
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbAPI,
    private val dispatchers: DispatchersProvider,
    private val prefs: PrefsManager
) : MovieRepository {

    override suspend fun getMovies(filter: MovieFilter) = withContext(dispatchers.io()) {
        val genresMapDef = async { getGenres().associateBy { it.id } }
        val moviesListDef = async {
            when (filter) {
                MovieFilter.NOW_PLAYING -> api.getNowPlaying().results
                MovieFilter.POPULAR -> api.getPopular().results
                MovieFilter.TOP_RATED -> api.getTopRated().results
                MovieFilter.UPCOMING -> api.getUpcoming().results
            }
        }

        val moviesList = moviesListDef.await()
        val genresMap = genresMapDef.await()

        moviesList.map { movie ->
            Movie(
                id = movie.id,
                minimumAge = if (movie.adult) 16 else 13,
                posterPath = createPathToImage(movie.posterPath),
                genres = movie.genreIds.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                title = movie.originalTitle,
                runtime = Random.nextInt(61, 197)
            )
        }
    }

    override suspend fun getMovieDetails(movieId: Int) = withContext(dispatchers.io()) {
        val movie = api.getDetails(movieId)

        MovieDetails(
            id = movie.id,
            minimumAge = if (movie.adult) 16 else 13,
            backdropPath = createPathToImage(movie.backdropPath),
            genres = movie.genres.map {
                Genre(
                    id = it.id,
                    name = it.name
                )
            },
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            title = movie.originalTitle,
            overview = movie.overview
        )
    }

    override suspend fun getActors(movieId: Int) = withContext(dispatchers.io()) {
        api.getMovieCredits(movieId).cast.map {
            Actor(
                id = it.id,
                name = it.name,
                profilePath = createPathToImage(it.profilePath)
            )
        }
    }

    private suspend fun getGenres() = withContext(dispatchers.io()) {
        api.getGenresForMovies().genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    private suspend fun getConfiguration() =
        prefs.config ?: api.getConfiguration().also { prefs.config = it }

    private suspend fun createPathToImage(relativeUrl: String?) =
        relativeUrl?.let {
            "${getConfiguration().images.secureBaseUrl}$IMAGE_SIZE$it"
        }

    companion object {
        private const val IMAGE_SIZE = "w342"
    }
}
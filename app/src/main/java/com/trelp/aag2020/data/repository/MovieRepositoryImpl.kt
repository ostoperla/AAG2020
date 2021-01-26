package com.trelp.aag2020.data.repository

import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.mapper.GenreMapper
import com.trelp.aag2020.data.mapper.MovieDetailsMapper
import com.trelp.aag2020.data.mapper.MovieMapper
import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.domain.repository.MovieRepository
import com.trelp.aag2020.domain.entity.MovieFilter
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbAPI,
    private val dispatchers: DispatchersProvider,
    private val genreMapper: GenreMapper,
    private val movieMapper: MovieMapper,
    private val movieDetailsMapper: MovieDetailsMapper
) : MovieRepository {

    override suspend fun getMovies(filter: MovieFilter) = withContext(dispatchers.io) {
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

        moviesList.map {
            movieMapper.map(it, genresMap)
        }
    }

    override suspend fun getMovieDetails(movieId: Int) = withContext(dispatchers.io) {
        api.getMovieDetails(movieId).let {
            movieDetailsMapper.map(it)
        }
    }

    private suspend fun getGenres() = withContext(dispatchers.io) {
        api.getGenres().genres.map {
            genreMapper.map(it)
        }
    }
}
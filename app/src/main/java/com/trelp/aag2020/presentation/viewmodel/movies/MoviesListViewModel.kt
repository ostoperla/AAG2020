package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.*
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.MovieFilter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class MoviesListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        loadMoviesList()
    }

    private fun loadMoviesList() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                _movies.value = movieRepository.getMovies(MovieFilter.TOP_RATED)
            }
            Timber.d("$time ms")
        }
    }
}
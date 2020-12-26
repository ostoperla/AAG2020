package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.*
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.data.MoviesRepository
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        loadMoviesList()
    }

    private fun loadMoviesList() {
        viewModelScope.launch {
            _movies.value = moviesRepository.loadMovies()
        }
    }

    companion object {
        fun factory(moviesRepository: MoviesRepository) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>) =
                if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
                    MoviesListViewModel(moviesRepository) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
                }
        }
    }
}
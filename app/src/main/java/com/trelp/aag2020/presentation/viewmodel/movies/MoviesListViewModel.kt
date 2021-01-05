package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.*
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.data.MoviesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
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
}
package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.data.MoviesRepository
import com.trelp.aag2020.domain.entity.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel constructor(
    private val moviesRepository: MoviesRepository,
    private val movieId: Int,
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _movie.value = moviesRepository.loadMovie(movieId)
        }
    }
}
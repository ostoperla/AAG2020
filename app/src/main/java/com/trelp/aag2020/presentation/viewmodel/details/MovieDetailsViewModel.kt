package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.MovieDetails
import kotlinx.coroutines.launch

class MovieDetailsViewModel constructor(
    private val movieRepository: MovieRepository,
    private val movieId: Int,
) : ViewModel() {

    private val _movie = MutableLiveData<MovieDetails>()
    val movie: LiveData<MovieDetails>
        get() = _movie

    private val _actors = MutableLiveData<List<Actor>>()
    val actors: LiveData<List<Actor>>
        get() = _actors

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _movie.value = movieRepository.getMovieDetails(movieId)
            _actors.value = movieRepository.getActors(movieId)
        }
    }

    companion object {
        fun factory(
            movieRepository: MovieRepository,
            movieId: Int
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>) =
                if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                    MovieDetailsViewModel(movieRepository, movieId) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
                }
        }
    }
}
package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trelp.aag2020.data.MoviesRepository

class MovieDetailsViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val movieId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            MovieDetailsViewModel(moviesRepository, movieId) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
}
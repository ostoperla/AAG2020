package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trelp.aag2020.domain.interactor.MovieInteractor

class MovieDetailsViewModelFactory(
    private val movieInteractor: MovieInteractor,
    private val movieId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            MovieDetailsViewModel(movieInteractor, movieId) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
}
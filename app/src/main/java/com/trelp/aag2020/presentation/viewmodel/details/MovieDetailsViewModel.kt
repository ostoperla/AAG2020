package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel.ViewState.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailsViewModel constructor(
    private val movieRepository: MovieRepository,
    private val movieId: Int,
) : ViewModel() {

    private var currentState: ViewState = Loading
    private val _movie = MutableLiveData(currentState)
    val movie: LiveData<ViewState>
        get() = _movie

    init {
        loadMovieDetails()
    }

    private fun refreshDetails() {
        _movie.value = proceed(Action.Refresh)
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            val action = try {
                val details = movieRepository.getMovieDetails(movieId)
                Action.LoadData(details)
            } catch (e: Throwable) {
                Action.Error(e)
            }
            _movie.value = proceed(action)
        }
    }

    private fun proceed(action: Action): ViewState {
        Timber.d("Action ${action.javaClass.simpleName}")
        val newState = reducer(action, currentState)
        if (newState != currentState) {
            currentState = newState
            Timber.d("      ViewState ${currentState.javaClass.simpleName}")
        }
        return currentState
    }

    private fun reducer(action: Action, state: ViewState) = when (state) {
        Loading -> when (action) {
            is Action.LoadData -> Data(action.data)
            is Action.Error -> Error(action.error)
            else -> state
        }
        is Data -> state
        is Error -> when (action) {
            Action.Refresh -> Loading
            else -> state
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val data: MovieDetails) : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }

    sealed class Action {
        object Refresh : Action()
        data class LoadData(val data: MovieDetails) : Action()
        data class Error(val error: Throwable) : Action()
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
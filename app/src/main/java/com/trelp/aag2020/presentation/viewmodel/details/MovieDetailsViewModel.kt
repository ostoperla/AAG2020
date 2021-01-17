package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewModel
import com.trelp.aag2020.presentation.viewmodel.details.ViewState.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel constructor(
    private val movieRepository: MovieRepository,
    private val movieId: Int,
) : BaseViewModel<Action, ViewState>(Loading) {

    init {
        loadMovieDetails()
    }

//    private fun refreshDetails() {
//        stateMutableLiveData.value = proceed(Action.Refresh)
//        loadMovieDetails()
//    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            val action = try {
                val details = movieRepository.getMovieDetails(movieId)
                Action.LoadData(details)
            } catch (e: Throwable) {
                Action.Error(e)
            }
            stateMutableLiveData.value = proceed(action)
        }
    }

    override fun reducer(action: Action) = when (currentState) {
        Loading -> when (action) {
            is Action.LoadData -> Data(action.data)
            is Action.Error -> Error(action.error)
            else -> currentState
        }
        is Data -> currentState
        is Error -> when (action) {
            Action.Refresh -> Loading
            else -> currentState
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
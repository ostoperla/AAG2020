package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.interactor.MovieInteractor
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewModel
import com.trelp.aag2020.presentation.viewmodel.movies.ViewState.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : BaseViewModel<Action, ViewState>(EmptyProgress) {

    init {
        loadMovies()
    }

    fun refreshMovies() {
        proceed(Action.Refresh)
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val action = try {
                val movies = movieInteractor.getMovies()
                if (movies.isNullOrEmpty()) {
                    Action.EmptyData
                } else {
                    Action.LoadData(movies)
                }
            } catch (e: Throwable) {
                Action.Error(e)
            }
            proceed(action)
        }
    }

    override fun reducer(action: Action) = when (currentState) {
        EmptyProgress -> when (action) {
            is Action.LoadData -> Data(action.data)
            is Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
            else -> currentState
        }
        is Refresh -> when (action) {
            is Action.LoadData -> Data(action.data)
            is Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
            else -> currentState
        }
        is Data -> when (action) {
            Action.Refresh -> Refresh
            is Action.LoadData -> Data(action.data)
            is Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
        }
        Empty, is Error -> when (action) {
            Action.Refresh -> EmptyProgress
            else -> currentState
        }
    }
}
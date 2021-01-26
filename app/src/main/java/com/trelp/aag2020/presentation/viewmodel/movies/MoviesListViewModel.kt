package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.interactor.MovieInteractor
import com.trelp.aag2020.presentation.viewmodel.common.BaseAction
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewModel
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : BaseViewModel<MoviesListViewModel.Action, MoviesListViewModel.ViewState>(ViewState.EmptyProgress) {

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
        ViewState.EmptyProgress -> when (action) {
            is Action.LoadData -> ViewState.Data(action.data)
            is Action.EmptyData -> ViewState.Empty
            is Action.Error -> ViewState.Error(action.error)
            else -> currentState
        }
        is ViewState.Refresh -> when (action) {
            is Action.LoadData -> ViewState.Data(action.data)
            is Action.EmptyData -> ViewState.Empty
            is Action.Error -> ViewState.Error(action.error)
            else -> currentState
        }
        is ViewState.Data -> when (action) {
            Action.Refresh -> ViewState.Refresh
            is Action.LoadData -> ViewState.Data(action.data)
            is Action.EmptyData -> ViewState.Empty
            is Action.Error -> ViewState.Error(action.error)
        }
        ViewState.Empty, is ViewState.Error -> when (action) {
            Action.Refresh -> ViewState.EmptyProgress
            else -> currentState
        }
    }

    sealed class Action : BaseAction {
        object Refresh : Action()
        data class LoadData(val data: List<Movie>) : Action()
        object EmptyData : Action()
        data class Error(val error: Throwable) : Action()
    }

    sealed class ViewState : BaseViewState {
        object EmptyProgress : ViewState()
        object Refresh : ViewState()
        data class Data(val data: List<Movie>) : ViewState()
        object Empty : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }
}
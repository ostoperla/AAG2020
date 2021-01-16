package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.*
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.domain.entity.MovieFilter
import com.trelp.aag2020.presentation.viewmodel.movies.MoviesListViewModel.ViewState.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var currentState: ViewState = EmptyProgress
    private val _movies = MutableLiveData(currentState)
    val movies: LiveData<ViewState>
        get() = _movies

    init {
        loadMovies()
    }

    fun refreshMovies() {
        _movies.value = proceed(Action.Refresh)
        loadMovies()
    }

    private fun loadMovies(filter: MovieFilter = MovieFilter.TOP_RATED) {
        viewModelScope.launch {
            val action = try {
                val movies = movieRepository.getMovies(filter)
                if (movies.isNullOrEmpty()) {
                    Action.EmptyData
                } else {
                    Action.LoadData(movies)
                }
            } catch (e: Throwable) {
                Action.Error(e)
            }
            _movies.value = proceed(action)
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
        EmptyProgress -> when (action) {
            is Action.LoadData -> Data(action.data)
            is Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
            else -> state
        }
        is Refresh -> when (action) {
            is Action.LoadData -> Data(action.data)
            is Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
            else -> state
        }
        is Data -> when (action) {
            Action.Refresh -> Refresh(state.data)
            is Action.LoadData -> Data(action.data)
            is Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
        }
        Empty, is Error -> when (action) {
            Action.Refresh -> EmptyProgress
            else -> state
        }
    }

    sealed class ViewState {
        object EmptyProgress : ViewState()
        data class Refresh(val data: List<Movie>) : ViewState()
        data class Data(val data: List<Movie>) : ViewState()
        object Empty : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }

    sealed class Action {
        object Refresh : Action()
        data class LoadData(val data: List<Movie>) : Action()
        object EmptyData: Action()
        data class Error(val error: Throwable) : Action()
    }
}
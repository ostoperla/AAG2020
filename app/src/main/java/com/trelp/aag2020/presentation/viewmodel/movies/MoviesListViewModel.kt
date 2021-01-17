package com.trelp.aag2020.presentation.viewmodel.movies

import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.MovieFilter
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewModel
import com.trelp.aag2020.presentation.viewmodel.movies.ViewState.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel<Action, ViewState>(EmptyProgress) {

    init {
        loadMovies()
    }

    fun refreshMovies() {
        stateMutableLiveData.value = proceed(Action.Refresh)
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
            stateMutableLiveData.value = proceed(action)
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
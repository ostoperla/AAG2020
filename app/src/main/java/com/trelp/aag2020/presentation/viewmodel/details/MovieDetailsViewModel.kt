package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.domain.interactor.MovieInteractor
import com.trelp.aag2020.presentation.viewmodel.common.BaseAction
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewModel
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewState
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel.Action
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel.ViewState
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel.ViewState.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel constructor(
    private val movieInteractor: MovieInteractor,
    private val movieId: Int,
) : BaseViewModel<Action, ViewState>(Loading) {

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            val action = try {
                val details = movieInteractor.getMovieDetails(movieId)
                Action.LoadData(details)
            } catch (e: Throwable) {
                Action.Error(e)
            }
            proceed(action)
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

    sealed class Action : BaseAction {
        object Refresh : Action()
        data class LoadData(val data: MovieDetails) : Action()
        data class Error(val error: Throwable) : Action()
    }

    sealed class ViewState : BaseViewState {
        object Loading : ViewState()
        data class Data(val data: MovieDetails) : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }
}
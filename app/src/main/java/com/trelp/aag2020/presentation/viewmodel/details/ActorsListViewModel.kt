package com.trelp.aag2020.presentation.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.interactor.ActorInteractor
import com.trelp.aag2020.presentation.viewmodel.common.BaseAction
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewModel
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewState
import com.trelp.aag2020.presentation.viewmodel.details.ActorsListViewModel.Action
import com.trelp.aag2020.presentation.viewmodel.details.ActorsListViewModel.ViewState
import com.trelp.aag2020.presentation.viewmodel.details.ActorsListViewModel.ViewState.*
import kotlinx.coroutines.launch

class ActorsListViewModel constructor(
    private val actorInteractor: ActorInteractor,
    private val movieId: Int,
) : BaseViewModel<Action, ViewState>(EmptyProgress) {

    init {
        loadActors()
    }

    fun refreshActors() {
        proceed(Action.Refresh)
        loadActors()
    }

    private fun loadActors() {
        viewModelScope.launch {
            val action = try {
                val actors = actorInteractor.getActors(movieId)
                if (actors.isNullOrEmpty()) {
                    Action.EmptyData
                } else {
                    Action.NewData(actors)
                }
            } catch (e: Throwable) {
                Action.Error(e)
            }
            proceed(action)
        }
    }

    override fun reducer(action: Action) = when (currentState) {
        EmptyProgress -> when (action) {
            is Action.NewData -> Data(action.data)
            Action.EmptyData -> Empty
            is Action.Error -> Error(action.error)
            else -> currentState
        }
        is Data -> currentState
        Empty, is Error -> when (action) {
            Action.Refresh -> EmptyProgress
            else -> currentState
        }
    }

    sealed class Action : BaseAction {
        object Refresh : Action()
        data class NewData(val data: List<Actor>) : Action()
        object EmptyData : Action()
        data class Error(val error: Throwable) : Action()
    }

    sealed class ViewState : BaseViewState {
        object EmptyProgress : ViewState()
        data class Data(val data: List<Actor>) : ViewState()
        object Empty : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }

    companion object {
        fun factory(
            actorInteractor: ActorInteractor,
            movieId: Int
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                if (modelClass.isAssignableFrom(ActorsListViewModel::class.java)) {
                    ActorsListViewModel(actorInteractor, movieId) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
                }
        }
    }
}
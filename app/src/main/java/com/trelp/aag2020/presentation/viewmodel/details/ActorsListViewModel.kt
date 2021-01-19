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
) : BaseViewModel<Action, ViewState>(Loading) {

    init {
        loadActors()
    }

    private fun loadActors() {
        viewModelScope.launch {
            val action = try {
                val actors = actorInteractor.getActors(movieId)
                Action.LoadData(actors)
            } catch (e: Throwable) {
                Action.Error(e)
            }
            stateMutableLiveData.value = proceed(action)
        }
    }

    override fun reducer(action: Action) = when (currentState) {
        else -> currentState
    }

    sealed class Action : BaseAction {
        data class LoadData(val data: List<Actor>) : Action()
        data class Error(val throwable: Throwable) : Action()
    }

    sealed class ViewState : BaseViewState {
        object Loading : ViewState()
    }

    companion object {
        fun factory(
            actorInteractor: ActorInteractor,
            movieId: Int
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>) =
                if (modelClass.isAssignableFrom(ActorsListViewModel::class.java)) {
                    ActorsListViewModel(actorInteractor, movieId) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
                }
        }
    }
}
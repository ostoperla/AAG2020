package com.trelp.aag2020.presentation.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import kotlin.properties.Delegates

abstract class BaseViewModel<A : BaseAction, S : BaseViewState>(initState: S) : ViewModel() {

    private var stateMutableLiveData = MutableLiveData(initState)
    val stateLiveData: LiveData<S>
        get() = stateMutableLiveData

    init {
        Timber.tag(javaClass.simpleName).d("Init ViewState: ${initState.javaClass.simpleName}")
    }

    protected var currentState by Delegates.observable(initState) { _, oldState, newState ->
        stateMutableLiveData.value = newState

        Timber.tag(javaClass.simpleName)
            .d("ViewState: ${oldState.javaClass.simpleName} -> ${newState.javaClass.simpleName}")
    }

    fun proceed(action: A) {
        Timber.tag(javaClass.simpleName).d("Action: ${action.javaClass.simpleName}")
        currentState = reducer(action)
    }

    protected abstract fun reducer(action: A): S
}
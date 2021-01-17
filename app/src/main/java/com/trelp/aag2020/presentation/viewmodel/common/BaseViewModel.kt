package com.trelp.aag2020.presentation.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel<A : BaseAction, S : BaseViewState>(initState: S) : ViewModel() {

    protected var currentState = initState
    protected var stateMutableLiveData = MutableLiveData(initState)
    val stateLiveData: LiveData<S>
        get() = stateMutableLiveData

    fun proceed(action: A): S {
        Timber.tag(javaClass.simpleName).d("Action ${action.javaClass.simpleName}")
        val newState = reducer(action)
        if (newState != currentState) {
            currentState = newState
            Timber.tag(javaClass.simpleName)
                .d("      ViewState ${currentState.javaClass.simpleName}")
        }
        return currentState
    }

    protected abstract fun reducer(action: A): S
}
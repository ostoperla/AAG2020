package com.trelp.aag2020.presentation.viewmodel.movies

import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.presentation.viewmodel.common.BaseAction

sealed class Action : BaseAction {
    object Refresh : Action()
    data class LoadData(val data: List<Movie>) : Action()
    object EmptyData : Action()
    data class Error(val error: Throwable) : Action()
}
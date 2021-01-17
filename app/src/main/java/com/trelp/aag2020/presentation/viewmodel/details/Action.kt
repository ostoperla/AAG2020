package com.trelp.aag2020.presentation.viewmodel.details

import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.presentation.viewmodel.common.BaseAction

sealed class Action : BaseAction {
    object Refresh : Action()
    data class LoadData(val data: MovieDetails) : Action()
    data class Error(val error: Throwable) : Action()
}
package com.trelp.aag2020.presentation.viewmodel.movies

import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewState

sealed class ViewState : BaseViewState {
    object EmptyProgress : ViewState()
    object Refresh : ViewState()
    data class Data(val data: List<Movie>) : ViewState()
    object Empty : ViewState()
    data class Error(val error: Throwable) : ViewState()
}
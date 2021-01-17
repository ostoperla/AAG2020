package com.trelp.aag2020.presentation.viewmodel.details

import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.presentation.viewmodel.common.BaseViewState

sealed class ViewState : BaseViewState {
    object Loading : ViewState()
    data class Data(val data: MovieDetails) : ViewState()
    data class Error(val error: Throwable) : ViewState()
}
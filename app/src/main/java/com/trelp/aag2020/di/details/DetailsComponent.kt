package com.trelp.aag2020.di.details

import com.trelp.aag2020.di.FragmentScope
import com.trelp.aag2020.di.ImComponent
import com.trelp.aag2020.presentation.view.details.FragmentMovieDetails
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface DetailsComponent : ImComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsComponent
    }

    fun inject(fragment: FragmentMovieDetails)
}

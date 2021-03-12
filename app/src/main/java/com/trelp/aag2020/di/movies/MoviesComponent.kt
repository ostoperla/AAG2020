package com.trelp.aag2020.di.movies

import com.trelp.aag2020.di.FragmentScope
import com.trelp.aag2020.di.ImComponent
import com.trelp.aag2020.presentation.view.movies.FragmentMoviesList
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface MoviesComponent : ImComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MoviesComponent
    }

    fun inject(fragment: FragmentMoviesList)
}

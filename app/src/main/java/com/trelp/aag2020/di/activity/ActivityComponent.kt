package com.trelp.aag2020.di.activity

import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.di.ImComponent
import com.trelp.aag2020.di.details.DetailsComponent
import com.trelp.aag2020.di.movies.MoviesComponent
import dagger.Subcomponent

@Subcomponent(
    modules = [
        MainModule::class,
        VMModule::class,
        NetworkModule::class
    ]
)
@ActivityScope
interface ActivityComponent : ImComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun moviesComponentFactory(): MoviesComponent.Factory
    fun detailsComponentFactory(): DetailsComponent.Factory
}
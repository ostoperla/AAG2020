package com.trelp.aag2020.di.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.di.ViewModelKey
import com.trelp.aag2020.presentation.view.TmdbViewModel
import com.trelp.aag2020.presentation.viewmodel.ViewModelFactory
import com.trelp.aag2020.presentation.viewmodel.movies.MoviesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface VMModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(TmdbViewModel::class)
    fun bindTmdbVM(tmdbViewModel: TmdbViewModel): ViewModel

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    fun bindMoviesListVM(moviesListViewModel: MoviesListViewModel): ViewModel

    @ActivityScope
    @Binds
    fun bindVMFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
package com.trelp.aag2020.di.activity

import com.trelp.aag2020.data.AppDispatchers
import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.MovieRepositoryImpl
import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.domain.MovieRepository
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @Provides
    @ActivityScope
    fun provideDispatchers(): DispatchersProvider {
        return AppDispatchers()
    }

    @Provides
    @ActivityScope
    fun provideMoviesRepository(
        tmdbApi: TmdbAPI,
        dispatchers: DispatchersProvider
    ): MovieRepository {
        return MovieRepositoryImpl(tmdbApi, dispatchers)
    }
}
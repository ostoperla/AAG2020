package com.trelp.aag2020.di.activity

import com.trelp.aag2020.data.AppDispatchers
import com.trelp.aag2020.data.ConfigurationCache
import com.trelp.aag2020.data.ConfigurationRepositoryImpl
import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.MovieRepositoryImpl
import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.domain.ConfigurationRepository
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
    fun provideConfigurationCache(): ConfigurationCache {
        return ConfigurationCache(Long.MAX_VALUE)
    }

    @Provides
    @ActivityScope
    fun provideMoviesRepository(
        tmdbApi: TmdbAPI,
        dispatchers: DispatchersProvider,
        configCache: ConfigurationCache
    ): MovieRepository {
        return MovieRepositoryImpl(tmdbApi, dispatchers, configCache)
    }

    @Provides
    @ActivityScope
    fun provideConfigurationRepository(
        tmdbApi: TmdbAPI,
        dispatchers: DispatchersProvider,
    ): ConfigurationRepository {
        return ConfigurationRepositoryImpl(tmdbApi, dispatchers)
    }
}
package com.trelp.aag2020.di.activity

import android.content.Context
import com.trelp.aag2020.data.AppDispatchers
import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.MovieRepositoryImpl
import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.data.storage.PrefsManager
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.di.AppContext
import com.trelp.aag2020.domain.MovieRepository
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json

@Module
object MainModule {

    @Provides
    @ActivityScope
    fun provideDispatchers(): DispatchersProvider {
        return AppDispatchers()
    }

    @Provides
    @ActivityScope
    fun providePrefsManager(
        @AppContext context: Context,
        json: Json
    ): PrefsManager {
        return PrefsManager(context, json)
    }

    @Provides
    @ActivityScope
    fun provideMoviesRepository(
        tmdbApi: TmdbAPI,
        dispatchers: DispatchersProvider,
        prefsManager: PrefsManager
    ): MovieRepository {
        return MovieRepositoryImpl(tmdbApi, dispatchers, prefsManager)
    }
}
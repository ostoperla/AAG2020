package com.trelp.aag2020.di.activity

import android.content.Context
import com.trelp.aag2020.data.AppDispatchers
import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.MovieRepositoryImpl
import com.trelp.aag2020.data.storage.PrefsManager
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.di.AppContext
import com.trelp.aag2020.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json

@Module
abstract class MainModule {

    @Binds
    @ActivityScope
    abstract fun bindDispatchers(dispatchersImpl: AppDispatchers): DispatchersProvider

    @Binds
    @ActivityScope
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    companion object {

        @Provides
        @ActivityScope
        fun providePrefsManager(@AppContext context: Context, json: Json): PrefsManager {
            return PrefsManager(context, json)
        }
    }
}
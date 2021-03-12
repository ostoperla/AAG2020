package com.trelp.aag2020.di.activity

import android.content.Context
import android.content.res.AssetManager
import com.trelp.aag2020.data.MoviesRepository
import com.trelp.aag2020.data.storage.LocalDataSource
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.di.AppContext
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json

@Module
object MainModule {

    @Provides
    @ActivityScope
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @ActivityScope
    fun provideAssetManager(@AppContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @ActivityScope
    fun provideLocalDataSource(
        assetManager: AssetManager,
        json: Json
    ): LocalDataSource {
        return LocalDataSource(assetManager, json)
    }

    @Provides
    @ActivityScope
    fun provideMoviesRepository(localDataSource: LocalDataSource): MoviesRepository {
        return MoviesRepository(localDataSource)
    }
}

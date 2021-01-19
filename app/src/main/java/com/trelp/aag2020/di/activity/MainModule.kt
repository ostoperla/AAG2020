package com.trelp.aag2020.di.activity

import android.content.Context
import com.trelp.aag2020.data.repository.ActorRepositoryImpl
import com.trelp.aag2020.data.AppDispatchers
import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.mapper.ActorMapper
import com.trelp.aag2020.data.mapper.GenreMapper
import com.trelp.aag2020.data.mapper.ImageUrlMapper
import com.trelp.aag2020.data.mapper.MovieDetailsMapper
import com.trelp.aag2020.data.mapper.MovieMapper
import com.trelp.aag2020.data.repository.MovieRepositoryImpl
import com.trelp.aag2020.data.storage.PrefsManager
import com.trelp.aag2020.di.ActivityScope
import com.trelp.aag2020.di.AppContext
import com.trelp.aag2020.domain.repository.ActorRepository
import com.trelp.aag2020.domain.repository.MovieRepository
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

    @Binds
    @ActivityScope
    abstract fun bindActorRepository(actorRepositoryImpl: ActorRepositoryImpl): ActorRepository

    companion object {

        @Provides
        @ActivityScope
        fun providePrefsManager(@AppContext context: Context, json: Json): PrefsManager {
            return PrefsManager(context, json)
        }

        @Provides
        @ActivityScope
        fun provideGenreMapper(): GenreMapper {
            return GenreMapper()
        }

        @Provides
        @ActivityScope
        fun provideImageUrlMapper(): ImageUrlMapper {
            return ImageUrlMapper()
        }

        @Provides
        @ActivityScope
        fun provideMovieMapper(imageUrlMapper: ImageUrlMapper): MovieMapper {
            return MovieMapper(imageUrlMapper)
        }

        @Provides
        @ActivityScope
        fun provideMovieDetailsMapper(
            imageUrlMapper: ImageUrlMapper,
            genreMapper: GenreMapper
        ): MovieDetailsMapper {
            return MovieDetailsMapper(imageUrlMapper, genreMapper)
        }

        @Provides
        @ActivityScope
        fun provideActorMapper(imageUrlMapper: ImageUrlMapper): ActorMapper {
            return ActorMapper(imageUrlMapper)
        }
    }
}
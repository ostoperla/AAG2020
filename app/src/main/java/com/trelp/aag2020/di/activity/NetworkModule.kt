package com.trelp.aag2020.di.activity

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.trelp.aag2020.BuildConfig
import com.trelp.aag2020.data.network.interceptor.AuthInterceptor
import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.data.network.interceptor.ErrorResponseInterceptor
import com.trelp.aag2020.di.ActivityScope
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

@Module
object NetworkModule {

    @Provides
    @ActivityScope
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @ActivityScope
    fun provideNetworkLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @ActivityScope
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @ActivityScope
    fun provideErrorResponseInterceptor(): ErrorResponseInterceptor {
        return ErrorResponseInterceptor()
    }

    @Provides
    @ActivityScope
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        errorResponseInterceptor: ErrorResponseInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
            addNetworkInterceptor(errorResponseInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
//                addNetworkInterceptor(loggingInterceptor)
            }
        }.build()
    }

    @Provides
    @ActivityScope
    @ExperimentalSerializationApi
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder().apply {
            client(okHttpClient)
            baseUrl(BuildConfig.TMDB_ENDPOINT)
            addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        }.build()
    }

    @Provides
    @ActivityScope
    fun provideTmdbAPI(retrofit: Retrofit): TmdbAPI {
        return retrofit.create()
    }
}
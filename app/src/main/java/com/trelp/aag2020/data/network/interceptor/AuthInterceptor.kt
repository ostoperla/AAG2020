package com.trelp.aag2020.data.network.interceptor

import com.trelp.aag2020.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val authUrl = original.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .build()

        val authRequest = original.newBuilder()
            .url(authUrl)
            .build()

        return chain.proceed(authRequest)
    }

    companion object {
        private const val API_KEY = "api_key"
    }
}
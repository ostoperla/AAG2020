package com.trelp.aag2020.data.network.interceptor

import com.trelp.aag2020.data.network.exception.NetworkError
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorResponseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val code = response.code
        if (code in 400..504) {
            throw NetworkError(code)
        }

        return response
    }
}
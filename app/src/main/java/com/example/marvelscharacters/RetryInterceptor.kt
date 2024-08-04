package com.example.marvelscharacters

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetry: Int) : Interceptor {
    private var retryCount = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        var exception: IOException? = null

        while (retryCount < maxRetry) {
            try {
                response = chain.proceed(chain.request())
                break
            } catch (e: IOException) {
                exception = e
                retryCount++
            }
        }

        if (response == null && exception != null) {
            throw exception
        }

        return response!!
    }
}
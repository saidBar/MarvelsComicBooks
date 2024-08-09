package com.example.marvelscharacters.di

import com.example.marvelscharacters.BuildConfig
import com.example.marvelscharacters.api.MarvelApiService
import com.example.marvelscharacters.utils.Constants.PUBLIC_API_KEY
import com.example.marvelscharacters.utils.Constants.BASE_URL
import com.example.marvelscharacters.utils.Constants.NETWORK_TIMEOUT
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object ApiModule {

    @Provides
    @Singleton
    fun ProvidesBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun ConnectionTimeOut() = NETWORK_TIMEOUT


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS) // Set connection timeout
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)    // Set read timeout
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)   // Set write timeout
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson :Gson, client: OkHttpClient) : MarvelApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MarvelApiService::class.java)
}

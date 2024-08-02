package com.example.marvelscomicbooks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<CharacterDataWrapper>
}
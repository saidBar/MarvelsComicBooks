package com.example.marvelscharacters

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
    ): Call<CharacterDataWrapper>
}
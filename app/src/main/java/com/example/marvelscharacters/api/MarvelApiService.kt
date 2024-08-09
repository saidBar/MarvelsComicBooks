package com.example.marvelscharacters.api

import com.example.marvelscharacters.response.CharacterDataWrapper
import com.example.marvelscharacters.response.CharacterDetailDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    fun getCharactersList(
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
    ): Call<CharacterDataWrapper>

    @GET("/v1/public/characters{characterId}")
    fun getCharacter(
        @Query("characterId") characterId: Int,
        @Query("apikey") apiKey: String,
    ): Call<CharacterDetailDataWrapper>
}
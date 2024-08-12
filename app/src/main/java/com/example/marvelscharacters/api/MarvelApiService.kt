package com.example.marvelscharacters.api

import com.example.marvelscharacters.response.CharacterDataWrapper
import com.example.marvelscharacters.response.CharacterDetailDataWrapper
import com.example.marvelscharacters.response.ComicDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    fun getCharactersList(
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
    ): Call<CharacterDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(
        @Path("characterId") characterId: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String,
    ): Call<CharacterDetailDataWrapper>

    @GET("/v1/public/characters/{characterId}/comics")
    fun getCharacterComics(
        @Path("characterId") characterId: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("format") format: String,
        @Query("formatType") formatType: String,
        @Query("dateRange") dateRange: String,
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Int,
    ): Call<ComicDataWrapper>
}
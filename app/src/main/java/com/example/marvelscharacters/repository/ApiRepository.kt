package com.example.marvelscharacters.repository

import com.example.marvelscharacters.api.MarvelApiService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiRepository: MarvelApiService?
) {

    fun getCharactersList(ts: String, apiKey: String, hash: String, limit: Int, offset:Int) =
        apiRepository?.getCharactersList(ts, apiKey, hash, limit, offset)
    fun getCharacter(characterId: Int, apiKey: String,ts: String, hash: String) =
        apiRepository?.getCharacter(characterId, apiKey, ts, hash )
    fun getCharacterComics(characterId: Int, ts: String, apiKey: String, hash: String, format: String, formatType: String, dateRange: String, orderBy: String, limit: Int) =
        apiRepository?.getCharacterComics(characterId, ts,apiKey, hash, format, formatType, dateRange, orderBy, limit, )

}
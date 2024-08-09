package com.example.marvelscharacters.repository

import com.example.marvelscharacters.api.MarvelApiService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiRepository: MarvelApiService?
) {

    fun getCharactersList(ts: String, apiKey: String, hash: String, limit: Int) =
        apiRepository?.getCharactersList(ts, apiKey, hash, limit)
    fun getCharacter(characterId: Int, apiKey: String) =
        apiRepository?.getCharacter(characterId, apiKey)

}
package com.zara.rickandmortyzaratest.data.remote

import com.zara.rickandmortyzaratest.data.remote.model.CharacterResponse
import com.zara.rickandmortyzaratest.data.remote.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    suspend fun getCharacters(@Query("page") page : Int ) : Response<CharactersResponse>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id : Int) : Response<CharacterResponse>
}
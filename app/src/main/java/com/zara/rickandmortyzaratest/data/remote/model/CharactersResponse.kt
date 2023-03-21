package com.zara.rickandmortyzaratest.data.remote.model

import com.google.gson.annotations.SerializedName
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain

data class CharactersResponse(
    val info: Info = Info(),
    @SerializedName("results")
    val results: List<CharacterResponse> = listOf()
)

fun CharactersResponse.toListDomain() : List<CharacterDomain> {
    return results.map { it.toDomain() }
}
package com.zara.rickandmortyzaratest.data.remote.model

import com.zara.rickandmortyzaratest.domain.model.CharacterDomain

data class CharacterResponse(
    val created: String = "",
    val episode: List<String> = listOf(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val location: Location = Location(),
    val name: String = "",
    val origin: Origin = Origin(),
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
)

fun CharacterResponse.toDomain() = CharacterDomain(
    id = id, name = name, status = status, species = species, gender = gender,
    origin = origin.toDomain(), location = location.toDomain(), image = image, type = type
)
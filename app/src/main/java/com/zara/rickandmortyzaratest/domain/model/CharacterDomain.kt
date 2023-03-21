package com.zara.rickandmortyzaratest.domain.model

data class CharacterDomain(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val origin: OriginDomain,
    val location: LocationDomain,
    val image: String = "",
    val type: String = ""
)

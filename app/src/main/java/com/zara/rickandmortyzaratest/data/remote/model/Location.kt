package com.zara.rickandmortyzaratest.data.remote.model

import com.zara.rickandmortyzaratest.domain.model.LocationDomain

data class Location(
    val name: String = "",
    val url: String = ""
)

fun Location.toDomain() = LocationDomain(name = name, url = url)
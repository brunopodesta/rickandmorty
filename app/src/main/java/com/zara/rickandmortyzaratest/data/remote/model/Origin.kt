package com.zara.rickandmortyzaratest.data.remote.model

import com.zara.rickandmortyzaratest.domain.model.OriginDomain

data class Origin(
    val name: String = "",
    val url: String = ""
)

fun Origin.toDomain() = OriginDomain(name = name, url = url)

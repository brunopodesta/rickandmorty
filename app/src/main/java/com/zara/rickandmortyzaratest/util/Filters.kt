package com.zara.rickandmortyzaratest.util

enum class StatusFilter(val status: String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown"),
    NONE("")
}

enum class GenderFilter(val gender: String) {
    FEMALE("Female"),
    MALE("Male"),
    GENDERLESS("Genderless"),
    UNKNOWN("Unknown"),
    NONE("")
}

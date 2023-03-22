package com.zara.rickandmortyzaratest.util

/**
 * Enum to indicate the Status of the character to filter the list
 */
enum class StatusFilter(val status: String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown"),
    NONE("")
}

/**
 * Enum to indicate the Gender of the character to filter the list
 */
enum class GenderFilter(val gender: String) {
    FEMALE("Female"),
    MALE("Male"),
    GENDERLESS("Genderless"),
    UNKNOWN("Unknown"),
    NONE("")
}

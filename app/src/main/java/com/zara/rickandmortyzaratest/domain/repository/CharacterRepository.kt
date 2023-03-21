package com.zara.rickandmortyzaratest.domain.repository

import com.zara.rickandmortyzaratest.domain.Resource
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain
import com.zara.rickandmortyzaratest.domain.model.CharactersDomain
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharactersList(page : Int) : Flow<Resource<List<CharacterDomain>>>

    suspend fun getCharacter(id : Int) : Resource<CharacterDomain>

}
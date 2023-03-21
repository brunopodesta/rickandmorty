package com.zara.rickandmortyzaratest.domain.use_case

import com.zara.rickandmortyzaratest.domain.Resource
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain
import com.zara.rickandmortyzaratest.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend operator fun invoke(id : Int) : Resource<CharacterDomain> {
        return repository.getCharacter(id)
    }
}
package com.zara.rickandmortyzaratest.domain.use_case

import com.zara.rickandmortyzaratest.domain.Resource
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain
import com.zara.rickandmortyzaratest.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend operator fun invoke(page: Int) : Flow<Resource<List<CharacterDomain>>> {
        return repository.getCharactersList(page)
    }
}
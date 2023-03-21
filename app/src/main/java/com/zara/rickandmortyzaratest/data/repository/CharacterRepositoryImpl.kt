package com.zara.rickandmortyzaratest.data.repository

import com.zara.rickandmortyzaratest.data.remote.RickAndMortyApi
import com.zara.rickandmortyzaratest.data.remote.model.toDomain
import com.zara.rickandmortyzaratest.data.remote.model.toListDomain
import com.zara.rickandmortyzaratest.domain.Resource
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain
import com.zara.rickandmortyzaratest.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(val api: RickAndMortyApi) : CharacterRepository {

    override fun getCharactersList(page: Int): Flow<Resource<List<CharacterDomain>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCharacters(page)
            if (response.isSuccessful) {
                val body = response.body()?.toListDomain()
                emit(Resource.Success(body))
            } else {
                emit(Resource.Error(message = "Something went wrong", data = null))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong", data = null))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = null
                )
            )
        }

    }

    override suspend fun getCharacter(id: Int): Resource<CharacterDomain> {
        return try {
            val response = api.getCharacter(id)
            if (response.isSuccessful) {
                val result = response.body()
                Resource.Success(result?.toDomain())
            } else {
                Resource.Error(message = "Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Something went wrong")
        }
    }
}
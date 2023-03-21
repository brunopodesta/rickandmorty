package com.zara.rickandmortyzaratest.di

import com.zara.rickandmortyzaratest.data.remote.RickAndMortyApi
import com.zara.rickandmortyzaratest.data.repository.CharacterRepositoryImpl
import com.zara.rickandmortyzaratest.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to provide instance of CharacterRepositoryImpl
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickAndMortyApi) =
        CharacterRepositoryImpl(api) as CharacterRepository

}
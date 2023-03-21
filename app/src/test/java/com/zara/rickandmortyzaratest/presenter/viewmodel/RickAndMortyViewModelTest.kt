package com.zara.rickandmortyzaratest.presenter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zara.rickandmortyzaratest.domain.Resource
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain
import com.zara.rickandmortyzaratest.domain.model.LocationDomain
import com.zara.rickandmortyzaratest.domain.model.OriginDomain
import com.zara.rickandmortyzaratest.domain.use_case.GetCharacterListUseCase
import com.zara.rickandmortyzaratest.presenter.state.CharacterListState
import com.zara.rickandmortyzaratest.util.GenderFilter
import com.zara.rickandmortyzaratest.util.StatusFilter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for RickAndMortyViewModel
 */

@ExperimentalCoroutinesApi
class RickAndMortyViewModelTest {

    @RelaxedMockK
    private lateinit var getCharacterListUseCase: GetCharacterListUseCase

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var rickAndMortyViewModel: RickAndMortyViewModel

    val characterList = listOf(
        CharacterDomain(
            1,
            "Rick Sanchez",
            "Alive",
            "Human",
            "Male",
            OriginDomain("Earth (C-137)", ""),
            LocationDomain("", ""),
            "",
            ""
        ),
        CharacterDomain(
            2,
            "Morty Smith",
            "Alive",
            "Human",
            "Male",
            OriginDomain("", ""),
            LocationDomain("", ""),
            "",
            ""
        ),
        CharacterDomain(
            3,
            "Summer Smith",
            "Alive",
            "Human",
            "Female",
            OriginDomain("", ""),
            LocationDomain("", ""),
            "",
            ""
        ),
        CharacterDomain(
            12,
            "Alexander",
            "Dead",
            "Human",
            "Male",
            OriginDomain("", ""),
            LocationDomain("", ""),
            "",
            ""
        ),
        CharacterDomain(
            4,
            "Beth Smith",
            "Dead",
            "Human",
            "Female",
            OriginDomain("", ""),
            LocationDomain("", ""),
            "",
            ""
        )
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        rickAndMortyViewModel = RickAndMortyViewModel(getCharacterListUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when there are no filters return the complete list`() = runBlocking {

        val flow = flow { emit(Resource.Success(characterList)) }

        coEvery { getCharacterListUseCase(1) } returns flow

        rickAndMortyViewModel.getCharacters()

        val state = rickAndMortyViewModel.state.first() as CharacterListState.Success
        assert(state.dataList == characterList)
    }

    @Test
    fun `when the set gender filter to Male retrieve only Male character`() = runBlocking {
        val flow = flow { emit(Resource.Success(characterList)) }

        coEvery { getCharacterListUseCase(1) } returns flow

        rickAndMortyViewModel.setGenderFilter(GenderFilter.MALE)
        rickAndMortyViewModel.applyFilters()

        val state = rickAndMortyViewModel.state.first() as CharacterListState.Success
        assert(state.dataList == characterList.filter { it.gender == "Male" })
    }


    @Test
    fun `when set gender filter to Female retrieve only Female character`() = runBlocking {
        val flow = flow { emit(Resource.Success(characterList)) }

        coEvery { getCharacterListUseCase(1) } returns flow

        rickAndMortyViewModel.setGenderFilter(GenderFilter.FEMALE)
        rickAndMortyViewModel.applyFilters()

        val state = rickAndMortyViewModel.state.first() as CharacterListState.Success
        assert(state.dataList == characterList.filter { it.gender == "Female" })
    }

    @Test
    fun `when set status filter to Alive retrieve only Alive characters`() = runBlocking {

        val flow = flow { emit(Resource.Success(characterList)) }

        coEvery { getCharacterListUseCase(1) } returns flow

        rickAndMortyViewModel.setStatusFilter(StatusFilter.ALIVE)
        rickAndMortyViewModel.applyFilters()

        val state = rickAndMortyViewModel.state.first() as CharacterListState.Success
        assert(state.dataList == characterList.filter { it.status == "Alive" })
    }

    @Test
    fun `when set status filter to Dead retrieve only Dead characters`() = runBlocking {

        val flow = flow { emit(Resource.Success(characterList)) }

        coEvery { getCharacterListUseCase(1) } returns flow

        rickAndMortyViewModel.setStatusFilter(StatusFilter.DEAD)
        rickAndMortyViewModel.applyFilters()

        val state = rickAndMortyViewModel.state.first() as CharacterListState.Success
        assert(state.dataList == characterList.filter { it.status == "Dead" })
    }

    @Test
    fun `when set status filter to Alive and gender to Male retrieve only Alive Male characters`() =
        runBlocking {

            val flow = flow { emit(Resource.Success(characterList)) }

            coEvery { getCharacterListUseCase(1) } returns flow

            rickAndMortyViewModel.setGenderFilter(GenderFilter.MALE)
            rickAndMortyViewModel.setStatusFilter(StatusFilter.ALIVE)
            rickAndMortyViewModel.applyFilters()

            val state = rickAndMortyViewModel.state.first() as CharacterListState.Success
            assert(state.dataList == characterList.filter { it.status == "Alive" }.filter { it.gender == "Male" })
        }


}
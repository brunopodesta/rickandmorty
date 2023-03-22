package com.zara.rickandmortyzaratest.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zara.rickandmortyzaratest.domain.Resource
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain
import com.zara.rickandmortyzaratest.domain.use_case.GetCharacterListUseCase
import com.zara.rickandmortyzaratest.presenter.state.CharacterListState
import com.zara.rickandmortyzaratest.util.FINAL_PAGE
import com.zara.rickandmortyzaratest.util.GenderFilter
import com.zara.rickandmortyzaratest.util.StatusFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model class
 */

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterListState>(CharacterListState.Loading())
    val state: StateFlow<CharacterListState> = _state

    private val _selectedCharacter = MutableLiveData<CharacterDomain>()
    val selectedCharacter get() = _selectedCharacter

    var currentListCharacters = mutableListOf<CharacterDomain>()

    private var currentPage = 1

    var filterGender: GenderFilter = GenderFilter.NONE
    var filterStatus: StatusFilter = StatusFilter.NONE

    var isLoading = true

    /**
     * Increase or Decrease page number to get Characters
     */

    fun movePage(increase: Boolean) {
        if (increase) {
            currentPage++
        } else {
            if (currentPage > 1) {
                currentPage--
            }
        }
        getCharacters()
    }

    /**
     * Function to retrieve Character List
     */

    fun getCharacters() {
        viewModelScope.launch {
            val showPrevious = currentPage > 1
            val showNext = currentPage < FINAL_PAGE
            getCharacterListUseCase(currentPage).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        currentListCharacters.clear()
                        currentListCharacters.addAll(result.data ?: emptyList())
                        _state.emit(
                            CharacterListState.Success(
                                filterList(
                                    result.data ?: emptyList()
                                ), showPrevious, showNext
                            )
                        )
                        isLoading = false
                    }
                    is Resource.Error -> {
                        _state.emit(CharacterListState.Error(result.message ?: "An error occurs"))
                        isLoading = false
                    }
                    is Resource.Loading -> {
                        _state.emit(CharacterListState.Loading())
                    }
                }
            }.launchIn(this)
        }
    }

    /**
     * Set the character selected from the List
     */
    fun onCharacterSelected(id: Int) {
        val character = currentListCharacters.find { it.id == id } ?: return
        _selectedCharacter.value = character
    }

    /**
     * Set the Status Filter
     */
    fun setStatusFilter(status: StatusFilter) {
        filterStatus = status
    }

    /**
     * Set the Gender Filter
     */

    fun setGenderFilter(gender: GenderFilter) {
        filterGender = gender
    }

    /**
     * Indicates if  filter is needed.
     */
    private var isFiltering = false

    fun applyFilters() {
        isFiltering = !(filterStatus == StatusFilter.NONE && filterGender == GenderFilter.NONE)
        getCharacters()
    }

    /**
     * Filter the list according to the filters set in the Filter Screen.
     * If no filter applied returns the original list
     */
    private fun filterList(characters: List<CharacterDomain>): List<CharacterDomain> {
        return if (isFiltering) {
            var filterList = characters
            if (filterStatus != StatusFilter.NONE) {
                filterList = filterList.filter { it.status == filterStatus.status }
            }

            if (filterGender != GenderFilter.NONE) {
                filterList = filterList.filter { it.gender == filterGender.gender }
            }
            filterList
        } else {
            characters
        }
    }


}
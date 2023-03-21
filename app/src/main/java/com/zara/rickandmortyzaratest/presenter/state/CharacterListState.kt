package com.zara.rickandmortyzaratest.presenter.state

import com.zara.rickandmortyzaratest.domain.model.CharacterDomain

sealed class CharacterListState {

    class Success(val dataList: List<CharacterDomain>, val showPrevious : Boolean, val showNext :Boolean) : CharacterListState()
    class Error(val error : String) : CharacterListState()
    class Loading() : CharacterListState()

}
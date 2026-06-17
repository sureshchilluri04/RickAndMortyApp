package com.example.rickandmortyapp.domain.characters.usecase

import com.example.rickandmortyapp.data.characters.DataServiceCharacters
import com.example.rickandmortyapp.domain.characters.entity.CharactersBaseResponse
import kotlinx.coroutines.flow.Flow

class DomainServiceCharacters {

    fun getCharacters(): Flow<CharactersBaseResponse> {
        return DataServiceCharacters().getCharacters()
    }
}
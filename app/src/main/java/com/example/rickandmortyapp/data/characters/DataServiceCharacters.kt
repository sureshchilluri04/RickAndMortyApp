package com.example.rickandmortyapp.data.characters

import com.example.rickandmortyapp.domain.characters.entity.CharactersBaseResponse
import kotlinx.coroutines.flow.flow

class DataServiceCharacters {

    fun getCharacters() = flow {
        try {
            val root = CharacterApiClient.apiService.getCharacters()
            emit(CharactersBaseResponse.OnSuccess(root.results))

        } catch (ex: Exception) {
            emit(CharactersBaseResponse.OnError("" + ex.message))
        }

    }
}
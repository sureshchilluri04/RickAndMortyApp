package com.example.rickandmortyapp.domain.characters.entity

import com.example.rickandmortyapp.data.Result

sealed class CharactersBaseResponse {
    data class OnSuccess(val results: List<Result>) : CharactersBaseResponse()
    data class OnError(val error: String) : CharactersBaseResponse()
}
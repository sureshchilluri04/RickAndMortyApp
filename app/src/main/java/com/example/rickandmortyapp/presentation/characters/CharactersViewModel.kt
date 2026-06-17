package com.example.rickandmortyapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.Result
import com.example.rickandmortyapp.domain.characters.entity.CharactersBaseResponse
import com.example.rickandmortyapp.domain.characters.usecase.DomainServiceCharacters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val stateFlow = MutableStateFlow<UIState>(UIState.initializer)

    val uiState: MutableStateFlow<UIState> get() = stateFlow

    fun getCharacters() {

        viewModelScope.launch {

            DomainServiceCharacters().getCharacters().onStart {
                stateFlow.value = UIState.IsLoading(true)
            }.catch { error ->
                stateFlow.value = UIState.IsLoading(false)
                stateFlow.value = UIState.OnError("" + error.message)
            }.collect { charactersBaseResponse ->
                stateFlow.value = UIState.IsLoading(false)

                when (charactersBaseResponse) {
                    is CharactersBaseResponse.OnSuccess -> {
                        stateFlow.value = UIState.OnSuccess(charactersBaseResponse.results)
                    }

                    is CharactersBaseResponse.OnError -> {
                        stateFlow.value = UIState.OnError(charactersBaseResponse.error)
                    }
                }
            }
        }
    }

    sealed class UIState {
        object initializer : UIState()
        data class IsLoading(val status: Boolean) : UIState()
        data class OnSuccess(val results: List<Result>) : UIState()
        data class OnError(val error: String) : UIState()
    }

}
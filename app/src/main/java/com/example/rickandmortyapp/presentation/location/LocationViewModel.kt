package com.example.rickandmortyapp.presentation.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.location.LocationDetails
import com.example.rickandmortyapp.domain.location.entity.LocationBaseResponse
import com.example.rickandmortyapp.domain.location.usecase.DomainServiceLocationDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val stateFlow = MutableStateFlow<UIState>(UIState.initializer)

    val uiState: MutableStateFlow<UIState> get() = stateFlow

    fun getLocationDetails(id: Int) {

        Log.i("AppLogs", "id: $id")
        viewModelScope.launch {

            DomainServiceLocationDetails().getLocationDetails(id).onStart {
                stateFlow.value = UIState.IsLoading(true)
            }.catch { error ->
                stateFlow.value = UIState.IsLoading(false)
                stateFlow.value = UIState.OnError("" + error.message)
            }.collect { locationBaseResponse ->
                stateFlow.value = UIState.IsLoading(false)

                when (locationBaseResponse) {
                    is LocationBaseResponse.OnSuccess -> {
                        stateFlow.value = UIState.OnSuccess(locationBaseResponse.locationDetails)
                    }

                    is LocationBaseResponse.OnError -> {
                        stateFlow.value = UIState.OnError(locationBaseResponse.error)
                    }
                }
            }
        }
    }

    sealed class UIState {
        object initializer : UIState()
        data class IsLoading(val status: Boolean) : UIState()
        data class OnSuccess(val locationDetails: LocationDetails) : UIState()
        data class OnError(val error: String) : UIState()
    }

}
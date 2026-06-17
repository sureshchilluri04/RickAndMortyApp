package com.example.rickandmortyapp.data.location

import com.example.rickandmortyapp.data.characters.CharacterApiClient
import com.example.rickandmortyapp.domain.location.entity.LocationBaseResponse
import kotlinx.coroutines.flow.flow

class DataServiceLocationDetails {

    fun getLocationDetails(id: Int) = flow {
        try {
            val locationDetails = CharacterApiClient.apiService.getLocationDetails(id)
            emit(LocationBaseResponse.OnSuccess(locationDetails))

        } catch (ex: Exception) {
            emit(LocationBaseResponse.OnError("" + ex.message))
        }

    }
}
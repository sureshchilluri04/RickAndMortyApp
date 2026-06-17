package com.example.rickandmortyapp.domain.location.usecase

import com.example.rickandmortyapp.data.location.DataServiceLocationDetails
import com.example.rickandmortyapp.domain.location.entity.LocationBaseResponse
import kotlinx.coroutines.flow.Flow

class DomainServiceLocationDetails {

    fun getLocationDetails(id: Int): Flow<LocationBaseResponse> {
        return DataServiceLocationDetails().getLocationDetails(id)
    }
}
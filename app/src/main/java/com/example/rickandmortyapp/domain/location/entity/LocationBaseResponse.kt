package com.example.rickandmortyapp.domain.location.entity

import com.example.rickandmortyapp.data.location.LocationDetails

sealed class LocationBaseResponse {
    data class OnSuccess(val locationDetails: LocationDetails) : LocationBaseResponse()
    data class OnError(val error: String) : LocationBaseResponse()
}
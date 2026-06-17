package com.example.rickandmortyapp.data.characters

import com.example.rickandmortyapp.data.Root
import com.example.rickandmortyapp.data.location.LocationDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    suspend fun getCharacters(): Root

    @GET("location/{id}")
    suspend fun getLocationDetails(@Path("id") Id: Int): LocationDetails
}
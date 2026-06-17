package com.example.rickandmortyapp.data.characters

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterRetrofitClient {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object CharacterApiClient {
    val apiService: ApiService by lazy {
        CharacterRetrofitClient.retrofit.create(ApiService::class.java)
    }
}
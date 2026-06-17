package com.example.rickandmortyapp.data.location

data class LocationDetails(
    val id: Long,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String,
)

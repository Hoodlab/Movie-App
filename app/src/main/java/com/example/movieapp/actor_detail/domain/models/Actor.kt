package com.example.movieapp.actor_detail.domain.models

data class Actor(
    val id: Int,
    val name: String,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthDay: String,
    val genderRole: String,
    val knownFor: String,
    val placeOfBirth: String,
    val popularity: Double,
    val profilePath: String
)

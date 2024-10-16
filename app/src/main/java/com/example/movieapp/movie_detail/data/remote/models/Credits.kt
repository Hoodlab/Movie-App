package com.example.movieapp.movie_detail.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    @SerialName("cast")
    val cast: List<CastDto>,
    @SerialName("crew")
    val crew: List<Crew>
)
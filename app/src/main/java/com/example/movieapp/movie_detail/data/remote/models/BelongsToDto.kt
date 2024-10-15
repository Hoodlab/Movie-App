package com.example.movieapp.movie_detail.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BelongsToDto(
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("poster_path")
    val posterPath: String?
)
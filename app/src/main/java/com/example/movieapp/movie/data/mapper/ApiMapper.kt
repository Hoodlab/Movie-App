package com.example.movieapp.movie.data.mapper

interface ApiMapper<Domain, Entity> {
    fun mapToDomain(apiDto: Entity): Domain
}
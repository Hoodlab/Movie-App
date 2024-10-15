package com.example.movieapp.movie_detail.data.mapper

interface ApiMapper<Domain, Entity> {
    fun mapToDomain(apiDto: Entity): Domain
}
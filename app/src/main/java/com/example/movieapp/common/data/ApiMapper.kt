package com.example.movieapp.common.data

interface ApiMapper<Domain, Entity> {
    fun mapToDomain(apiDto: Entity): Domain
}
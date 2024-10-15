package com.example.movieapp.actor_detail.data.mapper_impl

import com.example.movieapp.actor_detail.data.remote.models.ActorDto
import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.common.data.ApiMapper
import kotlin.math.round

class ActorMapperImpl : ApiMapper<Actor, ActorDto> {
    override fun mapToDomain(apiDto: ActorDto): Actor {
        val genderRole = if (apiDto.gender == 2) "Actor" else "Actress"
        return Actor(
            id = apiDto.id,
            name = apiDto.name,
            alsoKnownAs = apiDto.alsoKnownAs,
            biography = apiDto.biography,
            birthDay = apiDto.birthday,
            genderRole = genderRole,
            knownFor = apiDto.knownForDepartment,
            placeOfBirth = apiDto.placeOfBirth,
            popularity = round(apiDto.popularity),
            profilePath = apiDto.profilePath
        )
    }
}
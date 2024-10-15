package com.example.movieapp.actor_detail.data.repository

import com.example.movieapp.actor_detail.data.remote.api.ActorApiService
import com.example.movieapp.actor_detail.data.remote.models.ActorDto
import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.actor_detail.domain.repository.ActorRepository
import com.example.movieapp.common.data.ApiMapper
import com.example.movieapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ActorRepositoryImpl(
    private val actorApiService: ActorApiService,
    private val mapper: ApiMapper<Actor, ActorDto>
) : ActorRepository {
    override fun fetchActorDetail(actorId: Int): Flow<Response<Actor>> = flow {
        emit(Response.Loading())
        val actorDto = actorApiService.fetchActorDetail(actorId = actorId)
        val actor = mapper.mapToDomain(actorDto)
        emit(Response.Success(actor))
    }.catch { error ->
        error.printStackTrace()
        emit(Response.Error(error))
    }
}
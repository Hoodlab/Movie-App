package com.example.movieapp.actor_detail.domain.repository

import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface ActorRepository {
    fun fetchActorDetail(actorId: Int): Flow<Response<Actor>>
}
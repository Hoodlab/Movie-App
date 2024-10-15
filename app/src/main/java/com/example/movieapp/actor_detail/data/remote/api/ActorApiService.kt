package com.example.movieapp.actor_detail.data.remote.api

import com.example.movieapp.BuildConfig
import com.example.movieapp.actor_detail.data.remote.models.ActorDto
import com.example.movieapp.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val ACTOR_ID = "actor_id"

interface ActorApiService {
    @GET("${K.MOVIE_ACTOR_ENDPOINT}/{$ACTOR_ID}")
    suspend fun fetchActorDetail(
        @Path(ACTOR_ID) actorId: Int,
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
    ):ActorDto

}
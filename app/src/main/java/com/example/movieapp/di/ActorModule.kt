package com.example.movieapp.di

import com.example.movieapp.actor_detail.data.mapper_impl.ActorMapperImpl
import com.example.movieapp.actor_detail.data.remote.api.ActorApiService
import com.example.movieapp.actor_detail.data.remote.models.ActorDto
import com.example.movieapp.actor_detail.data.repository.ActorRepositoryImpl
import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.actor_detail.domain.repository.ActorRepository
import com.example.movieapp.common.data.ApiMapper
import com.example.movieapp.utils.K
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActorModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideActorRepository(
        actorApiService: ActorApiService,
        mapper: ApiMapper<Actor, ActorDto>
    ): ActorRepository = ActorRepositoryImpl(
        actorApiService, mapper
    )

    @Provides
    @Singleton
    fun provideActorMapper(): ApiMapper<Actor, ActorDto> = ActorMapperImpl()

    @Provides
    @Singleton
    fun provideActorService(): ActorApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(ActorApiService::class.java)
    }
}
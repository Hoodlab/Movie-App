package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.authentication.data.repositoryImpl.AuthRepositoryImpl
import com.example.movieapp.authentication.data.repositoryImpl.GoogleAuthClientImpl
import com.example.movieapp.authentication.domain.repository.AuthRepository
import com.example.movieapp.authentication.domain.repository.GoogleAuthClient
import com.example.movieapp.movie.data.mapper.ApiMapper
import com.example.movieapp.movie.data.mapper_impl.ApiMapperImpl
import com.example.movieapp.movie.data.remote.api.MovieApiService
import com.example.movieapp.movie.data.remote.models.MovieDto
import com.example.movieapp.movie.data.repository_impl.MovieRepositoryImpl
import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.movie.domain.repository.MovieRepository
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun authRepository(): AuthRepository = AuthRepositoryImpl()


    @Provides
    @Singleton
    fun provideSignInClient(@ApplicationContext context: Context): SignInClient =
        Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun googleAuthClient(
        @ApplicationContext context: Context,

        ): GoogleAuthClient =
        GoogleAuthClientImpl(Identity.getSignInClient(context))


}
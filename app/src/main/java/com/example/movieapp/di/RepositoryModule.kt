package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.authentication.data.repositoryImpl.AuthRepositoryImpl
import com.example.movieapp.authentication.data.repositoryImpl.GoogleAuthClientImpl
import com.example.movieapp.authentication.domain.repository.AuthRepository
import com.example.movieapp.authentication.domain.repository.GoogleAuthClient
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
object RepositoryModule {

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
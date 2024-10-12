package com.example.movieapp.authentication.domain.repository

import android.content.Intent
import android.content.IntentSender
import com.example.movieapp.utils.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface GoogleAuthClient {
    suspend fun signIn(): IntentSender?
    fun signInWithIntent(intent: Intent): Flow<Response<AuthResult?>>
}
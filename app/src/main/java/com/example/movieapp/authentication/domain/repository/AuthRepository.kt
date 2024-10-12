package com.example.movieapp.authentication.domain.repository

import com.example.movieapp.utils.Response
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: MutableStateFlow<FirebaseUser?>
    fun hasVerifiedUser(): Boolean
    fun hasUser(): Boolean
    fun getUserId(): String
    suspend fun sendVerificationEmail(): Flow<Response<Unit>>
    suspend fun login(email: String, password: String): Flow<Response<AuthResult?>>
    suspend fun createUser(email: String, password: String): Flow<Response<AuthResult?>>
    suspend fun signInWithCredentials(credential: AuthCredential): Flow<Response<AuthResult?>>
    suspend fun sendPasswordResetLink(email: String): Flow<Response<Boolean>>
    suspend fun reloadFirebaseUser(): Flow<Response<Boolean>>
    suspend fun revokeAccess(): Flow<Response<Boolean>>
    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<FirebaseUser?>
    fun getAuthVerificationState(viewModelScope: CoroutineScope): StateFlow<Boolean>
    fun signOut()

}

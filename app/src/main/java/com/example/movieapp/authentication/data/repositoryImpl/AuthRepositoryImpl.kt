package com.example.movieapp.authentication.data.repositoryImpl

import android.util.Log
import com.example.movieapp.authentication.domain.repository.AuthRepository
import com.example.movieapp.utils.Response
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await



class AuthRepositoryImpl : AuthRepository {

    companion object {
        const val TAG = "AuthRepositoryImpl"
    }

    override val currentUser: MutableStateFlow<FirebaseUser?>
        get() = MutableStateFlow(Firebase.auth.currentUser)

    override fun hasVerifiedUser(): Boolean {
        return Firebase.auth.currentUser?.isEmailVerified ?: false
    }

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun getUserId(): String {
        return Firebase.auth.currentUser?.uid.orEmpty()
    }


    override suspend fun sendVerificationEmail(): Flow<Response<Unit>> = flow {
        emit(Response.Loading())
        Firebase.auth.currentUser
            ?.sendEmailVerification()
            ?.await()
        emit(Response.Success(Unit))
    }.catch { e ->
        emit(Response.Error(e))
    }

    override suspend fun login(email: String, password: String): Flow<Response<AuthResult?>> =
        flow {
            emit(Response.Loading())
            val result = Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .await()
            emit(Response.Success(result))
        }.catch { e ->
            emit(Response.Error(e))
        }

    override suspend fun createUser(email: String, password: String): Flow<Response<AuthResult?>> =
        flow {
            emit(Response.Loading())
            val result = Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            emit(Response.Success(result))

        }.catch { e ->
            emit(Response.Error(e))
        }

    override suspend fun signInWithCredentials(credential: AuthCredential): Flow<Response<AuthResult?>> =
        flow {
            emit(Response.Loading())
            val result = Firebase.auth.signInWithCredential(credential)
                .await()
            currentUser.value = result.user
            emit(Response.Success(result))
        }.catch { e ->
            emit(Response.Error(e))
        }

    override suspend fun sendPasswordResetLink(email: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading())
        Firebase.auth.sendPasswordResetEmail(email)
            .await()
        emit(Response.Success(true))
    }.catch { e ->
        emit(Response.Error(e))
    }

    override suspend fun reloadFirebaseUser(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading())
        Firebase.auth.currentUser?.reload()?.await()
        Log.i(TAG, "reloadFirebaseUser: auth repo reloading user")
        emit(Response.Success(true))
    }.catch { e ->
        emit(Response.Error(e))
    }

    override suspend fun revokeAccess(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading())
        Firebase.auth.currentUser?.delete()?.await()
        emit(Response.Success(true))
    }.catch { e ->
        emit(Response.Error(e))
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            Firebase.auth.currentUser?.reload()
            trySend(auth.currentUser)
        }
        Firebase.auth.addAuthStateListener(authStateListener)
        awaitClose {
            Firebase.auth.removeAuthStateListener(authStateListener)
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Firebase.auth.currentUser)

    override fun getAuthVerificationState(viewModelScope: CoroutineScope): StateFlow<Boolean> =
        callbackFlow {
            val authStateListener = AuthStateListener { auth ->
                trySend(auth.currentUser?.isEmailVerified ?: false)
            }
            Firebase.auth.addAuthStateListener(authStateListener)
            awaitClose {
                Firebase.auth.removeAuthStateListener(authStateListener)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Firebase.auth.currentUser?.isEmailVerified ?: false
        )

    override fun signOut() {
        Firebase.auth.signOut()
        currentUser.value = null
    }
}
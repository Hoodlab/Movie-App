package com.example.movieapp.ui.authentication.login

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.authentication.domain.repository.AuthRepository
import com.example.movieapp.authentication.domain.repository.GoogleAuthClient
import com.example.movieapp.utils.collectAndHandle
import com.example.movieapp.utils.isValidEmail
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val googleAuthClient: GoogleAuthClient,
) : ViewModel() {
    var loginState by mutableStateOf(LoginState())
        private set


    companion object {
        const val TAG = "loginVm"
    }

    init {
        initializeCurrentUser()
    }

    private fun initializeCurrentUser() {
        viewModelScope.launch {
            repository.currentUser.collectLatest {
                loginState = loginState.copy(
                    currentUser = it
                )
            }
        }
    }

    fun loginEvent(loginEvent: LoginEvents) {
        when (loginEvent) {
            is LoginEvents.OnEmailChange -> {
                loginState = loginState.copy(
                    email = loginEvent.email
                )
            }

            is LoginEvents.OnPasswordChange -> {
                loginState = loginState.copy(
                    password = loginEvent.password
                )
            }

            is LoginEvents.Login -> {
                login()
            }

            is LoginEvents.OnResendVerification -> {
                viewModelScope.launch {
                    resendVerification()
                }
            }

            is LoginEvents.SignInWithGoogle -> {
                signInWithGoogle(loginEvent)
            }

            is LoginEvents.LogOut -> {
                repository.signOut()
            }

            is LoginEvents.RememberMe -> {
                loginState = loginState.copy(
                    rememberMe = loginEvent.value
                )
            }
        }
    }

    private fun signInWithGoogle(loginEvent: LoginEvents.SignInWithGoogle) {
        viewModelScope.launch {
            googleAuthClient.signInWithIntent(loginEvent.intent)
                .collectAndHandle(
                    onError = {
                        loginState = loginState.copy(
                            loginErrorMsg = it?.cause?.localizedMessage
                        )
                    },
                    onLoading = {
                        loginState = loginState.copy(isLoading = true)
                    }
                ) {
                    hasNotVerifiedThrowError()
                    loginState = loginState.copy(
                        isSuccessLogin = true,
                        isLoading = false,
                        loginErrorMsg = null
                    )
                }
        }
    }

    private fun validateLoginForm() =
        loginState.email.isNotBlank() && loginState.password.isNotBlank()

    private suspend fun resendVerification() {
        try {
            repository.sendVerificationEmail()
                .onEach {
                    loginState = loginState.copy(showResendBtn = false)
                }
                .catch {
                    loginState = loginState.copy(
                        loginErrorMsg = it.localizedMessage
                    )
                }
                .collect()

        } catch (e: Exception) {
            loginState = loginState.copy(
                loginErrorMsg = e.localizedMessage
            )
            e.printStackTrace()
        }
    }

    private fun login() = viewModelScope.launch {
        try {
            loginState = loginState.copy(
                loginErrorMsg = null
            )
            if (!validateLoginForm()) throw IllegalArgumentException("Password or Email can not be empty")
            if (!isValidEmail(loginState.email)) throw IllegalArgumentException("Invalid Email Address")
            loginState = loginState.copy(
                isLoading = true
            )
            repository.login(loginState.email, loginState.password)
                .catch {
                    loginState = loginState.copy(
                        isSuccessLogin = false,
                        isLoading = false,
                    )
                }
                .collectAndHandle(
                    onLoading = {
                        loginState = loginState.copy(
                            isLoading = true
                        )
                    },
                    onError = {
                        loginState = loginState.copy(
                            isSuccessLogin = false,
                            isLoading = false,
                            loginErrorMsg = it?.localizedMessage
                        )
                    }
                ) {
                    loginState = loginState.copy(
                        isSuccessLogin = true,
                        isLoading = false,
                    )
                }
            hasNotVerifiedThrowError()
        } catch (e: Exception) {
            loginState = loginState.copy(
                loginErrorMsg = e.localizedMessage
            )
        } finally {
            loginState = loginState.copy(
                isLoading = false
            )
        }
    }

    private fun hasNotVerifiedThrowError() {
        if (!repository.hasVerifiedUser()) {
            loginState = loginState.copy(showResendBtn = true)
            throw IllegalArgumentException(
                """
                 Verification email sent! Check your inbox (spam folder too) and click the link to start using your account.}
            """.trimIndent()
            )
        }
    }

    fun hasUserVerified(): Boolean = repository.hasUser() && repository.hasVerifiedUser()

    suspend fun signInWithGoogleIntentSender(): IntentSender? = googleAuthClient.signIn()

}


data class LoginState(
    val loginErrorMsg: String? = null,
    val isLoading: Boolean = false,
    val isValidEmailAddress: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isSuccessLogin: Boolean = false,
    val currentUser: FirebaseUser? = null,
    val isUSerVerified: Boolean? = null,
    val showResendBtn: Boolean = false,
    val rememberMe: Boolean = false,
)

sealed class LoginEvents {
    data class OnEmailChange(val email: String) : LoginEvents()
    data class OnPasswordChange(val password: String) : LoginEvents()
    data object Login : LoginEvents()
    data object OnResendVerification : LoginEvents()
    data class SignInWithGoogle(
        val intent: Intent,
    ) : LoginEvents()
    data object LogOut : LoginEvents()
    data class RememberMe(val value: Boolean) : LoginEvents()
}














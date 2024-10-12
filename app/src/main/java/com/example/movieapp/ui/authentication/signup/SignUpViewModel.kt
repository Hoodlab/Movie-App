package com.example.movieapp.ui.authentication.signup

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.authentication.domain.repository.AuthRepository
import com.example.movieapp.authentication.domain.repository.GoogleAuthClient
import com.example.movieapp.utils.Response
import com.example.movieapp.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val googleAuthClient: GoogleAuthClient,
) : ViewModel() {
    var signUpState by mutableStateOf(SignUpState())
        private set

    companion object {
        const val TAG = "signupVM"
    }

    fun signUpEvent(signUpEvents: SignUpEvents) {
        when (signUpEvents) {
            is SignUpEvents.OnEmailChange -> {
                signUpState = signUpState.copy(
                    email = signUpEvents.email
                )
            }


            is SignUpEvents.OnPasswordChange -> {
                signUpState = signUpState.copy(
                    password = signUpEvents.password
                )
            }

            is SignUpEvents.OnConfirmPasswordChange -> {
                signUpState = signUpState.copy(
                    confirmPassword = signUpEvents.confirmPassword
                )
            }


            is SignUpEvents.SignUp -> {
                createUser()
            }

            is SignUpEvents.OnIsEmailVerificationChange -> {
                signUpState = signUpState.copy(
                    isVerificationEmailSent = false
                )
            }

            is SignUpEvents.SignInWithGoogle -> {
                signInWithGoogle(signUpEvents)

            }

        }
    }

    private fun signInWithGoogle(signUpEvents: SignUpEvents.SignInWithGoogle) {
        viewModelScope.launch {
            googleAuthClient.signInWithIntent(signUpEvents.intent)
                .collectAndHandle(
                    onError = {
                        signUpState = signUpState.copy(
                            loginErrorMsg = it?.cause?.localizedMessage
                        )
                    },
                    onLoading = {
                        signUpState = signUpState.copy(isLoading = true)
                    }
                ) {
                    signUpState = signUpState.copy(
                        isSuccessLogin = true,
                        isLoading = false,
                        loginErrorMsg = null
                    )
                }
        }
    }

    suspend fun signInWithGoogleIntentSender(): IntentSender? = googleAuthClient.signIn()

    private fun validateSignUpForm() = signUpState.run {
        email.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty()
    }

    private fun createUser() = viewModelScope.launch {
        try {
            val isNotSamePassword: Boolean = signUpState.password != signUpState.confirmPassword
            if (!validateSignUpForm()) throw IllegalArgumentException("Fields Can not be empty")
            if (isNotSamePassword) throw IllegalArgumentException("Password do not Match")
            signUpState = signUpState.copy(
                isLoading = true,
                loginErrorMsg = null
            )
            repository.createUser(signUpState.email, signUpState.password).collectLatest {
                signUpState = when (it) {
                    is Response.Loading -> {
                        signUpState.copy(isLoading = true)
                    }

                    is Response.Success -> {
                        sendVerificationEmail()
                        signUpState.copy(isSuccessLogin = true, isLoading = false)
                    }

                    is Response.Error -> {
                        signUpState = signUpState.copy(isSuccessLogin = false, isLoading = false)
                        throw IllegalArgumentException(it.error)
                    }

                }
            }


        } catch (e: Exception) {
            signUpState = signUpState.copy(
                loginErrorMsg = e.localizedMessage
            )
        } finally {
            signUpState = signUpState.copy(
                isLoading = false
            )
        }

    }

    private suspend fun sendVerificationEmail() = repository.sendVerificationEmail()
        .onEach {
            signUpState = signUpState.copy(
                isVerificationEmailSent = true
            )
        }
        .catch {
            it.printStackTrace()
        }
        .collect()


}

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val isVerificationEmailSent: Boolean = false,
    val loginErrorMsg: String? = null,
)

sealed class SignUpEvents {
    data class OnEmailChange(val email: String) : SignUpEvents()
    data class OnPasswordChange(val password: String) : SignUpEvents()
    data class OnConfirmPasswordChange(val confirmPassword: String) : SignUpEvents()
    data object SignUp : SignUpEvents()
    data object OnIsEmailVerificationChange : SignUpEvents()
    data class SignInWithGoogle(
        val intent: Intent,
    ) : SignUpEvents()
}
package com.example.movieapp.ui.authentication.login

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.R
import com.example.movieapp.ui.authentication.components.AlternativeLoginOptions
import com.example.movieapp.ui.authentication.components.MovieTextField
import com.example.movieapp.ui.authentication.components.LoadingView
import com.example.movieapp.ui.authentication.login.LoginViewModel.Companion.TAG
import com.example.movieapp.ui.components.MovieAppBg
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hoods.com.handout.authentication_feature.presentation.components.HeaderText
import kotlinx.coroutines.launch

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    isVerificationEmailSent: Boolean,
    onSignUpClick: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    val loginState = viewModel.loginState
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val launcherGoogle = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                scope.launch {
                    viewModel.loginEvent(
                        LoginEvents.SignInWithGoogle(result.data ?: return@launch)
                    )

                }
            }
        }
    )
    LaunchedEffect(isVerificationEmailSent) {
        if (isVerificationEmailSent) {
            snackbarHostState.showSnackbar(
                "Verification Email Sent to ${loginState.currentUser?.email}"
            )
        }
    }
    LaunchedEffect(viewModel.hasUserVerified()) {
        Log.i(TAG, "LoginScreen: ${viewModel.hasUserVerified()}")
        if (viewModel.hasUserVerified()) {
            navigateToHomeScreen()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        MovieAppBg {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(defaultPadding)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HeaderText(
                        "Login to Your Account",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(vertical = defaultPadding)
                    )
                    Column() {
                        AnimatedVisibility(
                            loginState.loginErrorMsg != null
                        ) {
                            Text(
                                loginState.loginErrorMsg ?: "unknown",
                                color = MaterialTheme.colorScheme.error,
                                maxLines = 2
                            )
                        }
                        AnimatedVisibility(loginState.showResendBtn) {
                            TextButton(
                                onClick = {
                                    viewModel.loginEvent(
                                        LoginEvents.OnResendVerification
                                    )
                                }
                            ) {
                                Text("Resend Verification")
                            }
                        }
                        MovieTextField(
                            value = loginState.email,
                            onValueChange = {
                                viewModel.loginEvent(LoginEvents.OnEmailChange(it))
                            },
                            labelText = "Username",
                            leadingIcon = Icons.Default.Person,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(itemSpacing))
                        MovieTextField(
                            value = loginState.password,
                            onValueChange = {
                                viewModel.loginEvent(LoginEvents.OnPasswordChange(it))
                            },
                            labelText = "Password",
                            leadingIcon = Icons.Default.Lock,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardType = KeyboardType.Password
                        )
                        Spacer(Modifier.height(itemSpacing))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                loginState.rememberMe, {
                                    viewModel.loginEvent(
                                        LoginEvents.RememberMe(it)
                                    )
                                }
                            )
                            Text("Remember me")
                        }
                        TextButton(
                            onClick = onForgotPasswordClick,
                        ) {
                            Text("Forgot Password?")
                        }
                        Spacer(Modifier.height(itemSpacing))
                        Button(
                            onClick = {
                                viewModel.loginEvent(
                                    LoginEvents.Login
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Login")
                        }
                        Spacer(Modifier.height(itemSpacing))
                        AlternativeLoginOptions(
                            onIconClick = {
                                when (it) {
                                    0 -> {
                                        //TODO("Facebook Login")
                                    }

                                    1 -> {
                                        scope.launch {
                                            val googleIntentSender =
                                                viewModel.signInWithGoogleIntentSender()
                                            launcherGoogle.launch(
                                                IntentSenderRequest.Builder(
                                                    googleIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    }
                                }
                            },
                            iconList = listOf(
                                R.drawable.logos_facebook,
                                R.drawable.logos_google,
                                R.drawable.logo_apple
                            ),

                            )
                        Spacer(Modifier.height(itemSpacing))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Don't have an Account?")
                            Spacer(Modifier.height(itemSpacing))
                            TextButton(onSignUpClick) {
                                Text("Sign up")
                            }
                        }
                    }
                }
            }
        }
        LoadingView(loginState.isLoading)
    }

}
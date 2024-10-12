package com.example.movieapp.ui.authentication.signup

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.R
import com.example.movieapp.ui.authentication.components.AlternativeLoginOptions
import com.example.movieapp.ui.authentication.components.LoadingView
import com.example.movieapp.ui.authentication.components.MovieTextField
import com.example.movieapp.ui.authentication.login.defaultPadding
import com.example.movieapp.ui.components.MovieAppBg
import hoods.com.handout.authentication_feature.presentation.components.HeaderText
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    onLoginClick: () -> Unit,
    onNavigateToLoginScreen: (Boolean) -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val signUpState = viewModel.signUpState
    val scope = rememberCoroutineScope()


    val launcherGoogle = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    viewModel.signUpEvent(
                        SignUpEvents.SignInWithGoogle(result.data ?: return@launch)
                    )

                }
            }
        }
    )
    LaunchedEffect(signUpState.isVerificationEmailSent) {
        if (signUpState.isVerificationEmailSent) {
            onNavigateToLoginScreen(true)
            viewModel.signUpEvent(SignUpEvents.OnIsEmailVerificationChange)
        }
    }
    BackHandler {
        onBackButtonClicked()
    }

    MovieAppBg {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(defaultPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(signUpState.loginErrorMsg != null) {
                Text(
                    text = signUpState.loginErrorMsg ?: "unknown error",
                    color = MaterialTheme.colorScheme.error
                )
            }
            HeaderText(
                text = "Sign Up",
                modifier = Modifier
                    .padding(vertical = defaultPadding)
                    .align(alignment = Alignment.Start)
            )
            Spacer(Modifier.height(defaultPadding))
            MovieTextField(
                value = signUpState.email,
                onValueChange = { viewModel.signUpEvent(SignUpEvents.OnEmailChange(it)) },
                labelText = "Email",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.Person
            )
            Spacer(Modifier.height(defaultPadding))
            MovieTextField(
                value = signUpState.password,
                onValueChange = { viewModel.signUpEvent(SignUpEvents.OnPasswordChange(it)) },
                labelText = "Password",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Default.Lock
            )
            Spacer(Modifier.height(defaultPadding))
            MovieTextField(
                value = signUpState.confirmPassword,
                onValueChange = { viewModel.signUpEvent(SignUpEvents.OnConfirmPasswordChange(it)) },
                labelText = "Confirm Password",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Default.Lock
            )
            Spacer(Modifier.height(defaultPadding))
            Button(
                onClick = {
                    viewModel.signUpEvent(SignUpEvents.SignUp)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign up")
            }
            Spacer(Modifier.height(defaultPadding))
            AlternativeLoginOptions(
                onIconClick = {
                    when (it) {
                        0 -> {

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
                )
            )
            Spacer(Modifier.height(defaultPadding))
            val singTxt = "Sign In"
            val signInAnnotation = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    append("Already have an account?")
                }
                append(" ")
                withStyle(
                    SpanStyle(color = MaterialTheme.colorScheme.primary)
                ) {
                    pushStringAnnotation(singTxt, singTxt)
                    append(singTxt)
                }
            }
            ClickableText(
                signInAnnotation,
            ) { offset ->
                signInAnnotation.getStringAnnotations(offset, offset).forEach {
                    if (it.tag == singTxt) {
                        onLoginClick()
                    }
                }
            }


        }
    }
    LoadingView(signUpState.isLoading)


}
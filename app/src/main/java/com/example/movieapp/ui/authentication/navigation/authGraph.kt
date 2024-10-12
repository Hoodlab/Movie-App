package com.example.movieapp.ui.authentication.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.movieapp.ui.authentication.login.LoginScreen
import com.example.movieapp.ui.authentication.login.LoginViewModel
import com.example.movieapp.ui.authentication.signup.SignUpScreen
import com.example.movieapp.ui.navigation.MovieNavigationActions
import com.example.movieapp.ui.navigation.Route

fun NavGraphBuilder.authGraph(
    navAction: MovieNavigationActions,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    modifier: Modifier,
) {
    navigation(
        startDestination = Route.LoginScreen().routeWithArgs,
        route = Route.NESTED_AUTH_ROUTE,
    ) {
        composable(
            route = Route.LoginScreen().routeWithArgs,
            arguments = listOf(
                navArgument(name = Route.IS_EMAIL_SENT) {
                    type = NavType.BoolType
                    defaultValue = false // Default value when the argument is not provided

                }
            )
        ) { entry ->
            LoginScreen(
                onSignUpClick = {
                    navAction.navigateToSignUpScreen()
                },
                isVerificationEmailSent = entry.arguments?.getString(Route.IS_EMAIL_SENT)
                    .toBoolean(),
                onForgotPasswordClick = {

                },
                navigateToHomeScreen = {
                    navAction.navigateToHomeGraph()
                },
                modifier = modifier,
                viewModel = loginViewModel,
            )
        }
        composable(route = Route.SignupScreen().route) {
            SignUpScreen(
                onLoginClick = {
                    navAction.navigateToLoginScreenWithArgs(false)
                },
                onNavigateToLoginScreen = {
                    navAction.navigateToLoginScreenWithArgs(false)
                },
                onBackButtonClicked = {
                    navAction.navigateToLoginScreenWithArgs(false)
                },
                modifier = modifier,
            )
        }

    }
}
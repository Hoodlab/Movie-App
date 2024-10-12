package com.example.movieapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.movieapp.ui.authentication.login.LoginViewModel
import com.example.movieapp.ui.authentication.navigation.authGraph
import com.example.movieapp.ui.home.navigation.homeGraph

@Composable
fun MovieNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navAction: MovieNavigationActions,
    loginViewModel: LoginViewModel,
    startDestination: String,
    homeGraphStartDestination: String,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        authGraph(
            navAction, navController, loginViewModel, modifier
        )
        homeGraph(navAction, navController, modifier, homeGraphStartDestination)
    }

}




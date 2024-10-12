package com.example.movieapp.ui.home.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.movieapp.ui.navigation.MovieNavigationActions
import com.example.movieapp.ui.navigation.Route

fun NavGraphBuilder.homeGraph(
    navAction: MovieNavigationActions,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {

    navigation(startDestination = startDestination, route = Route.NESTED_HOME_ROUTE) {
        composable(route = Route.HomeScreen().route,
            enterTransition = { fadeIn() + scaleIn() },
            exitTransition = { fadeOut() + shrinkOut() }) {

        }


    }
}





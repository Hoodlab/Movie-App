package com.example.movieapp.ui.home.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.movieapp.ui.actor.ActorScreen
import com.example.movieapp.ui.home.HomeScreen
import com.example.movieapp.ui.movie_detail.MovieDetailScreen
import com.example.movieapp.ui.navigation.MovieNavigationActions
import com.example.movieapp.ui.navigation.Route
import com.example.movieapp.utils.K

fun NavGraphBuilder.homeGraph(
    navAction: MovieNavigationActions,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {

    navigation(
        startDestination = startDestination,
        route = Route.NESTED_HOME_ROUTE,

        ) {
        composable(route = Route.HomeScreen().route,
            enterTransition = { fadeIn() + scaleIn() },
            exitTransition = { fadeOut() + shrinkOut() }) {
            HomeScreen(
                onMovieClick = {
                    navAction.navigateToFilmScreenWithArgs(it)
                },
            )
        }

        composable(
            route = Route.FilmScreen().routeWithArgs,
            arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
        ) {
            MovieDetailScreen(
                onNavigateBack = { navController.navigateUp() },
                onMovieClick = {
                    navAction.navigateToFilmScreenWithArgs(it)
                },
                onActorClick = {
                    navAction.navigateToActorScreenWithArgs(it)
                },
            )
        }

        composable(
            route = Route.ActorScreen().routeWithArgs,
            arguments = listOf(navArgument(name = K.ACTOR_ID) { type = NavType.IntType })
        ) {
            ActorScreen()
        }
    }
}





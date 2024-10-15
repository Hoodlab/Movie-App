package com.example.movieapp.ui.navigation

import com.example.movieapp.utils.K

sealed class Route {
    companion object {
        const val NESTED_AUTH_ROUTE = "auth_route"
        const val NESTED_HOME_ROUTE = "home_route"
        const val IS_EMAIL_SENT = "isEmailSent"
    }

    data class LoginScreen(
        val route: String = "login",
        val routeWithArgs: String = "$route?$IS_EMAIL_SENT={$IS_EMAIL_SENT}",
    ) : Route() {
        fun getRouteWithArgs(isEmailVerified: Boolean = false): String {
            return "$route?$IS_EMAIL_SENT=$isEmailVerified"
        }
    }

    data class SignupScreen(val route: String = "signup") : Route()

    data class HomeScreen(val route: String = "homeScreen") : Route()
    data class FilmScreen(
        val route: String = "FilmScreen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }

    data class ActorScreen(
        val route: String = "FilmScreen",
        val routeWithArgs: String = "$route/{${K.ACTOR_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }


}





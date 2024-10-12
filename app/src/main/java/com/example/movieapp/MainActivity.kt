package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.authentication.login.LoginScreen
import com.example.movieapp.ui.navigation.MovieNavigationActions
import com.example.movieapp.ui.navigation.MovieNavigationGraph
import com.example.movieapp.ui.navigation.Route
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        val navAction = remember {
            MovieNavigationActions(navController)
        }
        val mainViewModel: MainViewModel = hiltViewModel()
        val authState by mainViewModel.authStateFlow.collectAsStateWithLifecycle()
        LaunchedEffect(true) {
            mainViewModel.reloadUser()
            mainViewModel.getAuthState()
        }
        val startDestination by remember {
            derivedStateOf {
                when (authState?.isEmailVerified) {
                    true -> {
                        Route.NESTED_HOME_ROUTE
                    }

                    else -> {
                        Route.NESTED_AUTH_ROUTE
                    }
                }
            }
        }
        val homeGraphStartDestination by mainViewModel.startDestination.collectAsState()
        LaunchedEffect(authState) {
            if (authState?.isEmailVerified == false) {
                navAction.navigateToLoginScreenWithArgs(false)
            }
        }



        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovieNavigationGraph(
                navController = navController,
                navAction = navAction,
                loginViewModel = hiltViewModel(),
                startDestination = startDestination,
                homeGraphStartDestination = homeGraphStartDestination,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {
        Greeting("Android")
    }
}
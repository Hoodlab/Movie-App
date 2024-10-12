package com.example.movieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.authentication.domain.repository.AuthRepository
import com.example.movieapp.ui.navigation.Route
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    val TAG = "mainVm"
    var authStateFlow: StateFlow<FirebaseUser?> = authRepository.getAuthState(viewModelScope)
        private set
    private val _startDestination: MutableStateFlow<String> =
        MutableStateFlow(Route.HomeScreen().route)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()


    fun getAuthState() {
        authStateFlow = authRepository.getAuthState(viewModelScope)
    }


    fun getAuthVerificationState() = authRepository.getAuthVerificationState(viewModelScope)
    fun reloadUser() {
        viewModelScope.launch { authRepository.reloadFirebaseUser() }
    }


}
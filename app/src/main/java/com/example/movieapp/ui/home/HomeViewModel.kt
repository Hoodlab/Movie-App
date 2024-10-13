package com.example.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.movie.domain.repository.MovieRepository
import com.example.movieapp.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        fetchMovie()
    }

    private fun fetchMovie() = viewModelScope.launch {
        repository.fetchMovie().collectAndHandle(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) {movies ->
            _homeState.update {
                it.copy(isLoading = false, error = null, movies = movies)
            }
        }
    }

}

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
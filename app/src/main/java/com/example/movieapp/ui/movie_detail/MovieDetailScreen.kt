package com.example.movieapp.ui.movie_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.ui.authentication.components.LoadingView
import com.example.movieapp.ui.movie_detail.components.DetailBodyContent
import com.example.movieapp.ui.movie_detail.components.DetailTopContent

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailViewModel: DetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick:(Int) -> Unit,
    onActorClick:(Int) -> Unit,
) {
    val state by movieDetailViewModel.detailState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxWidth()) {
        AnimatedVisibility(
            state.error != null,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                state.error ?: "unknown",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        AnimatedVisibility(visible = !state.isLoading) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * .4f
                val bodyItemHeight = boxHeight * .6f
                state.movieDetail?.let { movieDetail ->
                    DetailTopContent(
                        movieDetail = movieDetail, modifier = Modifier
                            .height(topItemHeight)
                            .align(Alignment.TopCenter)
                    )
                    DetailBodyContent(
                        movieDetail = movieDetail,
                        movies = state.movies,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(bodyItemHeight),
                        isMovieLoading = state.isMovieLoading,
                        fetchMovie = {
                            movieDetailViewModel.fetchMovie()
                        },
                        onMovieClick = onMovieClick,
                        onActorClick = onActorClick
                    )
                }
            }
        }
        IconButton(onClick = onNavigateBack, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
        }
    }
    LoadingView(isLoading = state.isLoading)
}
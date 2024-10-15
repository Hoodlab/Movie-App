package com.example.movieapp.ui.actor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.ui.actor.components.ActorBodyContent
import com.example.movieapp.ui.actor.components.ActorTopContent
import com.example.movieapp.ui.authentication.components.LoadingView

@Composable
fun ActorScreen(
    modifier: Modifier = Modifier,
    actorViewModel: ActorViewModel = hiltViewModel(),
    onNavigateBack:() -> Unit
) {
    val state by actorViewModel.actorState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
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
                val topItemHeight = boxHeight * .45f
                val bodyItemHeight = boxHeight * .55f
                state.actor?.let {
                    ActorTopContent(actor = it, modifier = Modifier.height(topItemHeight).align(Alignment.TopCenter))
                    ActorBodyContent(actor = it, modifier = Modifier.height(bodyItemHeight).align(Alignment.BottomCenter))
                }
            }
        }
        IconButton(onClick = onNavigateBack, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
        }
    }
    LoadingView(isLoading = state.isLoading)
}
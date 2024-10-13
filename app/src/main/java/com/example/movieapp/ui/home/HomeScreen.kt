package com.example.movieapp.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.ui.authentication.components.LoadingView
import com.example.movieapp.ui.authentication.login.defaultPadding
import com.example.movieapp.ui.authentication.login.itemSpacing
import com.example.movieapp.ui.home.components.BodyContent
import com.example.movieapp.ui.home.components.TopContent
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.movies.size }
    )
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged) {
            isAutoScrolling = false
        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < state.movies.size - 1) currentPage + 1 else 0
                scrollToPage(target)
            }
        }
    }
    AnimatedVisibility(visible = !state.isLoading) {
        Box(modifier = modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(defaultPadding),
                pageSize = PageSize.Fill,
                pageSpacing = itemSpacing
            ) { page ->
                if (isAutoScrolling) {
                    AnimatedContent(
                        targetState = page,
                        label = ""
                    ) { index ->
                        TopContent(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .heightIn(min = 300.dp),
                            movie = state.movies[index]
                        )
                    }
                } else {
                    TopContent(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .heightIn(min = 300.dp),
                        movie = state.movies[page]
                    )
                }
            }
            BodyContent(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .heightIn(max = 430.dp),
                movies = state.movies
            )
            AnimatedVisibility(
                state.error != null
            ) {
                Text(
                    state.error ?: "unknown",
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 2
                )
            }
        }
    }
    LoadingView(isLoading = state.isLoading)
}

@Preview(showSystemUi = true)
@Composable
private fun PrevHomeScreen() {
    HomeScreen()
}

package com.example.movieapp.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.ui.authentication.login.defaultPadding
import com.example.movieapp.ui.authentication.login.itemSpacing

@Composable
fun BodyContent(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = defaultPadding)
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent viewed",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "More Recent viewed"
                        )
                    }
                }
                LazyRow {
                    items(movies) {
                        MovieCoverImage(movie = it, onMovieClick = onMovieClick)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Trending now",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "More Recent viewed"
                        )
                    }
                }
                LazyRow {
                    items(movies) {
                        MovieCoverImage(movie = it, onMovieClick = onMovieClick)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevBodyContent() {
    //  BodyContent()
}


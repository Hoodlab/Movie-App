package com.example.movieapp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.ui.authentication.login.defaultPadding
import com.example.movieapp.ui.authentication.login.itemSpacing
import com.example.movieapp.utils.K

@Composable
fun MovieCoverImage(modifier: Modifier = Modifier,movie: Movie) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()
    Box(
        modifier = modifier
            .size(width = 150.dp, height = 250.dp)
            .padding(itemSpacing)
    ) {
        AsyncImage(
            model =imgRequest,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(MaterialTheme.shapes.medium)
                .shadow(elevation = 4.dp),
            contentScale = ContentScale.Crop
        )
        MovieCard(
            shapes = CircleShape,
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(4.dp)
        ) {
            Icon(imageVector = Icons.Default.Bookmark, contentDescription = "Bookmark", modifier = Modifier.padding(4.dp))
        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.Black.copy(.8f),
            contentColor = Color.White,
            shape = RoundedCornerShape(
                bottomEnd = 30.dp,
                bottomStart = 30.dp,
            )
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = movie.title, maxLines = 1)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevCoverImage() {
   // MovieCoverImage()
}
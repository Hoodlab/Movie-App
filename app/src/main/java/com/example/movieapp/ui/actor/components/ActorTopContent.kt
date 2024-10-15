package com.example.movieapp.ui.actor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.movie_detail.domain.models.MovieDetail
import com.example.movieapp.ui.authentication.login.defaultPadding
import com.example.movieapp.ui.authentication.login.itemSpacing
import com.example.movieapp.ui.home.components.MovieCard
import com.example.movieapp.ui.theme.primaryLightHighContrast
import com.example.movieapp.utils.K

@Composable
fun ActorTopContent(modifier: Modifier = Modifier, actor: Actor) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${actor.profilePath}")
        .crossfade(true)
        .build()
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = imgRequest,
            contentDescription = null, // decorative element
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop,
            onError = {
                it.result.throwable.printStackTrace()
            },
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )
        MovieCard(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = "Top ${actor.popularity} IMDb",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


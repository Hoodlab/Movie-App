package com.example.movieapp.ui.actor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.ui.authentication.login.defaultPadding
import com.example.movieapp.ui.authentication.login.itemSpacing

@Composable
fun ActorBodyContent(modifier: Modifier = Modifier, actor: Actor) {
    LazyColumn(modifier = modifier) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = actor.genderRole,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = actor.birthDay,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = actor.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = actor.biography,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = "Quick facts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = "Birth place: ${actor.placeOfBirth}",
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.bodyMedium

                    )
                    Text(
                        text = "Birthday: ${actor.birthDay}",
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    val nameAnnotatedString = buildAnnotatedString {
                        append("Also known as: ")
                        actor.alsoKnownAs.forEach {
                            append(it)
                            append(" • ")
                        }
                    }
                    Text(
                        text = nameAnnotatedString,
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Known for: ${actor.knownFor}",
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
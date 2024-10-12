package com.example.movieapp.ui.authentication.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.authentication.login.itemSpacing


@Composable
fun AlternativeLoginOptions(
    onIconClick: (index: Int) -> Unit,
    @DrawableRes iconList: List<Int>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )
            Text("Or Sign in with")
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
        }

        Row {
            iconList.forEachIndexed { index, icon ->

                Surface(
                    modifier = Modifier.padding(itemSpacing),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
                ) {
                    IconButton(
                        onClick = { onIconClick(index) },
                    ) {
                        Icon(
                            painter = painterResource(icon),
                            "login with Google or Facebook",
                            modifier = Modifier
                                .size(32.dp),
                            tint = if (index == 2) MaterialTheme.colorScheme.onSurface else Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}
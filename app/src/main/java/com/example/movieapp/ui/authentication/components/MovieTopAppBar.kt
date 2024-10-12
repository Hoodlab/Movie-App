package com.example.movieapp.ui.authentication.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    onNavigateBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    titleContent: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = titleContent,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                "Navigate Back",
                modifier = Modifier
                    .clickable {
                        onNavigateBack()
                    }
            )
        },
        actions = actions
    )
}
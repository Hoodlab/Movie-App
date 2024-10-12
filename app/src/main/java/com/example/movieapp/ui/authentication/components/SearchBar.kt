package com.example.movieapp.ui.authentication.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.authentication.login.itemSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandoutSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText:String = "Search",
    onSearch: () -> Unit,
) {
    SearchBar(
        query = value,
        onQueryChange = onValueChange,
        onSearch = { onSearch() },
        placeholder = { Text(placeHolderText) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                null
            )
        },
        modifier = Modifier
            .height(70.dp)
            .padding(itemSpacing),
        active = false,
        onActiveChange = {},
        content = {}
    )


}

@Preview(showBackground = true)
@Composable
fun PrevSearchBar() {
    HandoutSearchBar("", {}, onSearch = {})
}
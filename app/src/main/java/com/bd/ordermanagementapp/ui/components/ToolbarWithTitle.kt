package com.bd.ordermanagementapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithTitle(title: String) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            // Optional: Add a navigation icon (e.g., back button)
            // IconButton(onClick = { /* Handle navigation */ }) {
            //     Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            // }
        },
        actions = {
            // Optional: Add action buttons
            // IconButton(onClick = { /* Handle action */ }) {
            //     Icon(Icons.Filled.Settings, contentDescription = "Settings")
            // }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ToolbarPreview() {
    ToolbarWithTitle(title = "My App Title")
}
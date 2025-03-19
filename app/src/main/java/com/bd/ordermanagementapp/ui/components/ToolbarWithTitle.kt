package com.bd.ordermanagementapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithTitle(title: String, navigationControllerToPopBack: NavHostController? = null) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            navigationControllerToPopBack?.let { navController ->
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
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
package com.bd.ordermanagementapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (state.campaigns.isEmpty()) {
            viewModel.fetchCampaigns()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
    ) {
        Text(
            text = state.loadingCampaigns.toString(),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = state.campaigns.toString(),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = state.errorCampaigns.orEmpty(),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}
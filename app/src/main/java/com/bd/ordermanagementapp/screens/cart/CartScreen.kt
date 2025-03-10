package com.bd.ordermanagementapp.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel


@Composable
fun CartScreen(viewModel: CartViewModel = koinViewModel(), padding: PaddingValues) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCart()
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(DustyWhite)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

        }

        Text("bebelak", modifier = Modifier.align(Alignment.BottomCenter))

        if (state.loadingCart) {
            ProgressView()
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    CartScreen(padding = PaddingValues(16.dp))
}
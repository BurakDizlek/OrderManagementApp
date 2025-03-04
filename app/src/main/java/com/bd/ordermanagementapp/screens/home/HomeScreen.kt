package com.bd.ordermanagementapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bd.data.model.Campaign
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
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
            .background(DustyWhite),
    ) {
        //Campaigns
        Text(
            stringResource(R.string.home_campaigns_title),
            modifier = Modifier.largePadding(),
            style = MaterialTheme.typography.titleMedium
        )

        if (state.loadingCampaigns) {
            ProgressView()
        }
        if (state.errorCampaigns?.isNotEmpty() == true) {
            ErrorView(errorMessage = state.errorCampaigns.orEmpty()) {
                viewModel.fetchCampaigns()
            }
        }
        if (state.campaigns.isNotEmpty()) {
            HorizontalCarousel(state.campaigns)
        }

        //Menu
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCarousel(campaigns: List<Campaign>) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { campaigns.size },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        preferredItemWidth = 260.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val campaign = campaigns[i]

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //todo navigate campaign details!
                }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = campaign.imageUrl,
                    contentDescription = "Campaign",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = campaign.description,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}
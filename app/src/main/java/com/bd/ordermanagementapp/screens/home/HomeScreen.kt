package com.bd.ordermanagementapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.bd.data.model.Campaign
import com.bd.data.model.MenuItem
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.home.campaign.CampaignDetailsScreenData
import com.bd.ordermanagementapp.screens.home.campaign.navigateToCampaignDetails
import com.bd.ordermanagementapp.screens.home.common.HomeCommonView
import com.bd.ordermanagementapp.screens.home.common.MenuItemView
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.NotificationPermissionHandler
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController,
    padding: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()
    val commonState by viewModel.commonUiState.collectAsState()
    val menuListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (state.campaigns.isEmpty()) {
            viewModel.fetchCampaigns()
        }
        if (state.menuItems.isEmpty()) {
            viewModel.fetchMenuItems()
        }
    }

    // Auto-load more items when reaching the end of the list
    LaunchedEffect(menuListState) {
        snapshotFlow {
            menuListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleItemIndex ->
            if (lastVisibleItemIndex != null && lastVisibleItemIndex >= state.menuItems.size - 1 && state.hasMoreMenuItems && state.errorMenuItems == null) {
                viewModel.fetchMenuItems()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = menuListState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(DustyWhite)
        ) {
            item {
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
                    HorizontalCarousel(state.campaigns, navController)
                }
            }

            //Menu
            itemsIndexed(state.menuItems) { index: Int, item: MenuItem ->
                if (index % 2 == 0) {
                    Row {
                        // First item in the row
                        MenuItemView(item, viewModel)
                        if (index < state.menuItems.size - 1) {
                            MenuItemView(
                                state.menuItems[index + 1],
                                viewModel
                            )
                        }
                    }
                }
            }
            if (state.loadingMenuItems) {
                item {
                    ProgressView()
                }
            }

            if (state.errorMenuItems?.isNotEmpty() == true) {
                item {
                    ErrorView(errorMessage = state.errorMenuItems.orEmpty()) {
                        viewModel.fetchMenuItems()
                    }
                }
            }
        }
        HomeCommonView(commonState, viewModel, navController)

        NotificationPermissionHandler()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCarousel(campaigns: List<Campaign>, navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val preferredItemWidth: Dp = remember { screenWidth * 3 / 4 }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { campaigns.size },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        preferredItemWidth = preferredItemWidth,
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
                    val data = CampaignDetailsScreenData(
                        name = campaign.name,
                        description = campaign.description,
                        image = campaign.imageUrl,
                        menuItemIds = campaign.menuItemIds
                    )
                    navController.navigateToCampaignDetails(data)
                }) {
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

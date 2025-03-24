package com.bd.ordermanagementapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.bd.data.extensions.formatPrice
import com.bd.data.model.Campaign
import com.bd.data.model.MenuItem
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.main.GraphRoute
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import com.bd.ordermanagementapp.ui.components.DecisionDialog
import com.bd.ordermanagementapp.ui.components.ErrorDialog
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.extensions.smallPadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigationController: NavController,
    padding: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()
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
                    HorizontalCarousel(state.campaigns)
                }
            }

            //Menu
            itemsIndexed(state.menuItems) { index: Int, item: MenuItem ->
                if (index % 2 == 0) {
                    Row {
                        // First item in the row
                        MenuItemView(item, viewModel, navigationController)
                        if (index < state.menuItems.size - 1) {
                            MenuItemView(
                                state.menuItems[index + 1],
                                viewModel,
                                navigationController
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
        if (state.loadingOrderOrCart) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.errorOrderOrCart?.let { errorMessage ->
            ErrorDialog(
                errorMessage = errorMessage,
                onDismiss = {
                    viewModel.onErrorOkButtonClicked()
                }
            )
        }

        state.orderOrCartDecisionMenuItemId?.let { menuItemId ->
            DecisionDialog(
                title = stringResource(R.string.decision_title),
                message = stringResource(R.string.cart_or_order_decision_message),
                rightButtonClick = { viewModel.onAddToCartClicked(menuItemId = menuItemId) },
                leftButtonClick = {
                    viewModel.onOrderNowButtonClicked(menuItemId = menuItemId)
                },
                onDismiss = { viewModel.onOrderOrCartDecisionDialogDismiss() },
                rightButtonText = stringResource(R.string.add_to_cart),
                leftButtonText = stringResource(R.string.order_now)
            )
        }

        if (state.displayNeedLoginDialog) {
            DecisionDialog(
                title = stringResource(R.string.login),
                message = stringResource(R.string.you_need_to_login_to_order),
                rightButtonClick = {
                    navigationController.navigate(GraphRoute.LOGIN)
                },
                leftButtonClick = {},
                onDismiss = { viewModel.onNeedToLoginDialogDismiss() },
                rightButtonText = stringResource(R.string.login),
                leftButtonText = stringResource(R.string.close)
            )
        }

        state.quickOrderMenuItemId?.let { menuItemId ->
            viewModel.onQuickOrderNavigationCompleted()
            navigationController.navigate(
                CreateOrderRoute.Starter(
                    isQuickOrder = true,
                    menuItemId = menuItemId
                )
            )
        }
    }
}


@Composable
fun RowScope.MenuItemView(
    item: MenuItem,
    viewModel: HomeViewModel,
    navigationController: NavController
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.space_small)),
        modifier = Modifier
            .fillMaxWidth()
            .weight(0.5f)
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.space_small))
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            modifier = Modifier
                .size(120.dp)
                .fillMaxWidth()
        )
        Text(text = item.name, modifier = Modifier.smallPadding())

        if (item.firstPrice > 0.0 && item.firstPrice > item.lastPrice) {
            val price = item.firstPrice.formatPrice(currency = item.currency)
            Text(
                text = AnnotatedString(
                    text = price, spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(textDecoration = TextDecoration.LineThrough),
                            start = 0,
                            end = price.length
                        )
                    )
                ), modifier = Modifier.smallPadding()
            )
        } else {
            Text(text = "", modifier = Modifier.smallPadding())
        }

        Text(
            text = item.lastPrice.formatPrice(currency = item.currency),
            modifier = Modifier.smallPadding()
        )

        IconButton(
            onClick = {
                viewModel.onAddButtonClicked(item.id)
            }, modifier = Modifier
                .smallPadding(includeBottom = true)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(1.dp, Color.Gray, CircleShape)
                .align(alignment = Alignment.End)

        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Button")
        }
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

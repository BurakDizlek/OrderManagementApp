package com.bd.ordermanagementapp.screens.home.campaign

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.home.common.HomeCommonView
import com.bd.ordermanagementapp.screens.home.common.MenuItemView
import com.bd.ordermanagementapp.ui.components.BannerCard
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampaignDetailsScreen(
    viewModel: CampaignDetailsViewModel = koinViewModel(),
    navController: NavHostController,
    data: CampaignDetailsScreenData?,
) {

    val state by viewModel.uiState.collectAsState()
    val commonState by viewModel.commonUiState.collectAsState()

    LaunchedEffect(Unit) {
        if (state.menuItems.isEmpty()) {
            data?.menuItemIds?.let {
                viewModel.fetchMenuItems(it)
            } ?: run {
                viewModel.showDataNullError()
            }
        }
    }

    Scaffold(topBar = {
        ToolbarWithTitle(
            title = stringResource(R.string.campaign_details_title),
            navigationControllerToPopBack = navController
        )
    }, content = { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DustyWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                data?.image?.let { imageUrl ->
                    BannerCard(
                        imageUrl = imageUrl,
                        cardTitle = data.name,
                        description = data.description,
                    )
                }

                state.menuItems.forEachIndexed { index, menuItem ->
                    if (index % 2 == 0) {
                        Row {
                            // First item in the row
                            MenuItemView(menuItem, viewModel)
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
                    ProgressView()
                }

                if (state.errorMenuItems?.isNotEmpty() == true) {
                    ErrorView(errorMessage = state.errorMenuItems.orEmpty()) {
                        viewModel.fetchMenuItems(data?.menuItemIds.orEmpty())
                    }
                }
            }

            if (state.showDataNullError) {
                ErrorView(stringResource(R.string.campaign_details_data_null)) {
                    navController.popBackStack()
                }
            }
            HomeCommonView(commonState, viewModel, navController)
        }
    })
}
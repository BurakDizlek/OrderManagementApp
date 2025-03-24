package com.bd.ordermanagementapp.screens.home.campaign

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampaignDetailsScreen(
    viewModel: CampaignDetailsViewModel = koinViewModel(),
    navController: NavHostController,
    data: CampaignDetailsScreenData?
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

    OrderManagementAppTheme {
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
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

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
}
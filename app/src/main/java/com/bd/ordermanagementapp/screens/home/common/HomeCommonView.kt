package com.bd.ordermanagementapp.screens.home.common

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.main.GraphRoute
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import com.bd.ordermanagementapp.ui.components.DecisionDialog
import com.bd.ordermanagementapp.ui.components.ErrorDialog

@Composable
fun BoxScope.HomeCommonView(
    state: HomeCommonUiViewState,
    viewModel: HomeCommonViewModel,
    navigationController: NavController
) {
    if (state.loadingAddToCart) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }

    state.errorAddToCart?.let { errorMessage ->
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
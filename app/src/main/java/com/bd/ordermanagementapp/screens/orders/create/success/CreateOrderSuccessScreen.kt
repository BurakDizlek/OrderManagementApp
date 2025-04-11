package com.bd.ordermanagementapp.screens.orders.create.success

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.main.BottomBarScreen
import com.bd.ordermanagementapp.screens.main.GraphRoute
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.extensions.mediumPadding


@Composable
fun CreateOrderSuccessScreen(data: CreateOrderRoute.Success, navController: NavHostController) {
    Scaffold(
        topBar = {
            ToolbarWithTitle(title = stringResource(R.string.create_order_success_title))
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.create_order_success_message),
                    modifier = Modifier
                        .fillMaxWidth()
                        .mediumPadding()
                )
                Text(
                    text = stringResource(R.string.order_id_with_text, data.orderId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .mediumPadding()
                )
                Button(
                    onClick = {
                        navigateToOrders(navController)
                    }, Modifier
                        .fillMaxWidth()
                        .mediumPadding()
                ) {
                    Text(text = stringResource(R.string.go_to_orders))
                }
            }
        })


    BackHandler(enabled = true) {
        //preventing back press
        navigateToOrders(navController)
    }
}

private fun navigateToOrders(navController: NavHostController) {
    navController.navigate(BottomBarScreen.Orders.route) {
        popUpTo(GraphRoute.BOTTOM_BAR) {
            inclusive = false
        }
    }
}
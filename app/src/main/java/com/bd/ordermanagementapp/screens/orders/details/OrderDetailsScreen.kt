package com.bd.ordermanagementapp.screens.orders.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bd.data.extensions.formatMillisDateSmart
import com.bd.data.extensions.formatPrice
import com.bd.data.model.order.Order
import com.bd.data.model.order.OrderStatus
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.KeyValue
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.extensions.mediumPadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = koinViewModel(),
    orderId: String,
    navController: NavHostController
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOrderId(orderId = orderId)
    }

    OrderManagementAppTheme {
        Scaffold(
            topBar = {
                ToolbarWithTitle(
                    title = stringResource(R.string.order_details_title),
                    navigationControllerToPopBack = navController
                )
            },
            content = { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(DustyWhite)
                ) {

                    state.order?.let {
                        OrderDetailsItem(order = it)
                    }

                    if (state.loading) {
                        ProgressView(modifier = Modifier.align(Alignment.Center))
                    }

                    state.errorMessage?.let {
                        ErrorView(it) {
                            viewModel.getOrderId(orderId)
                        }
                    }
                }
            })

    }
}

@Composable
fun OrderDetailsItem(modifier: Modifier = Modifier, order: Order) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.space_small)),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.space_medium)),
    ) {
        Column(modifier = Modifier.mediumPadding(includeBottom = true)) {
            KeyValue(key = stringResource(R.string.order_id), value = order.id)
            KeyValue(key = stringResource(R.string.statuses), value = order.statusText)
            KeyValue(
                key = stringResource(R.string.created_time),
                value = order.orderCreatedTime.formatMillisDateSmart()
            )
            KeyValue(
                key = stringResource(R.string.last_update_time),
                value = order.statusChangedTime.formatMillisDateSmart()
            )
            KeyValue(
                key = stringResource(R.string.content),
                value = order.orderItems.joinToString(", ") { it.name }
            )
            KeyValue(
                key = stringResource(R.string.total_price_label),
                value = order.totalPrice.formatPrice(order.currency)
            )

            KeyValue(
                key = stringResource(R.string.address),
                value = order.deliveryAddress
            )

            order.note?.let {
                KeyValue(
                    key = stringResource(R.string.note),
                    value = it
                )
            }

            if (order.status == OrderStatus.OPEN) {
                Button(
                    onClick = {
                        // todo cancel
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }

            if (order.status == OrderStatus.ON_THE_WAY) {
                Button(
                    onClick = {
                        // todo confirm received
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(R.string.confirm_received))
                }
            }
        }
    }
}
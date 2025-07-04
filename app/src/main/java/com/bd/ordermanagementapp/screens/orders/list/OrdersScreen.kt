package com.bd.ordermanagementapp.screens.orders.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bd.data.extensions.formatMillisDateSmart
import com.bd.data.extensions.formatPrice
import com.bd.data.model.order.Order
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.details.navigateToOrderDetails
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.KeyValue
import com.bd.ordermanagementapp.ui.components.NotificationPermissionHandler
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.components.filter.OrderFilterComponent
import com.bd.ordermanagementapp.ui.extensions.mediumPadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = koinViewModel(),
    navController: NavHostController,
    parentPadding: PaddingValues,
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOrders()
    }

    Scaffold(
        topBar = {
            ToolbarWithTitle(stringResource(R.string.orders_screen_title))
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(DustyWhite)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    OrderFilterComponent(
                        initialFilter = state.filterData,
                        onFilterChanged = { newFilter ->
                            viewModel.onFilterDataChanged(filterOrderData = newFilter)
                        }
                    )

                    state.errorMessage?.let {
                        ErrorView(it) {
                            viewModel.getOrders()
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = parentPadding.calculateBottomPadding())
                    ) {
                        items(state.orders.size) { index ->
                            OrderItemView(order = state.orders[index], goToDetails = {
                                navController.navigateToOrderDetails(orderId = state.orders[index].id)
                            })
                        }
                    }
                }

                if (state.loading) {
                    ProgressView(modifier = Modifier.align(Alignment.Center))
                }

                if (state.isOrdersEmpty) {
                    Text(
                        text = stringResource(R.string.there_are_no_orders),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp
                    )
                }
                NotificationPermissionHandler()
            }
        }
    )
}

@Composable
fun OrderItemView(modifier: Modifier = Modifier, order: Order, goToDetails: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.space_small)),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.space_medium)),
        onClick = goToDetails
    ) {
        Column(modifier = Modifier.mediumPadding(includeBottom = true)) {
            KeyValue(key = stringResource(R.string.statuses), value = order.statusText)
            KeyValue(
                key = stringResource(R.string.created_time),
                value = order.orderCreatedTime.formatMillisDateSmart()
            )
            KeyValue(
                key = stringResource(R.string.content),
                value = order.content
            )
            KeyValue(
                key = stringResource(R.string.total_price_label),
                value = order.totalPrice.formatPrice(order.currency)
            )
            Button(
                onClick = goToDetails,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(R.string.details))
            }
        }
    }
}
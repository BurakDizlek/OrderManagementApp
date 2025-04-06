package com.bd.ordermanagementapp.screens.orders.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.bd.ordermanagementapp.ui.components.DecisionDialog
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.KeyValue
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.extensions.mediumPadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = koinViewModel(),
    orderId: String,
    navController: NavHostController,
) {

    val state by viewModel.uiState.collectAsState()

    var showCancelDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showStartDeliveryDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


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
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            content = { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(DustyWhite)
                ) {
                    state.order?.let {
                        OrderDetailsItem(
                            order = it,
                            onCancelClicked = {
                                showCancelDialog = true
                            }, onConfirmClicked = {
                                showConfirmDialog = true
                            },
                            isManager = viewModel.isManager(),
                            onStartDeliveryClicked = {
                                showStartDeliveryDialog = true
                            }
                        )
                    }

                    if (state.loading) {
                        ProgressView(modifier = Modifier.align(Alignment.Center))
                    }

                    state.errorMessage?.let {
                        ErrorView(it) {
                            viewModel.getOrderId(orderId)
                        }
                    }

                    if (showCancelDialog) {
                        DecisionDialog(
                            title = stringResource(R.string.cancel_order_dialog_title),
                            message = stringResource(R.string.cancel_order_dialog_message),
                            rightButtonClick = {
                                viewModel.cancelOrder(orderId = orderId)
                            },
                            leftButtonClick = {
                            },
                            onDismiss = {
                                showCancelDialog = false
                            },
                            rightButtonText = stringResource(R.string.yes),
                            leftButtonText = stringResource(R.string.no)
                        )
                    }

                    if (showConfirmDialog) {
                        DecisionDialog(
                            title = stringResource(R.string.confirm_order_received_dialog_title),
                            message = stringResource(R.string.confirm_order_received_dialog_message),
                            rightButtonClick = {
                                viewModel.confirmOrderReceived(orderId = orderId)
                            },
                            leftButtonClick = {
                            },
                            onDismiss = {
                                showConfirmDialog = false
                            },
                            rightButtonText = stringResource(R.string.yes),
                            leftButtonText = stringResource(R.string.no)
                        )
                    }

                    if (showStartDeliveryDialog) {
                        DecisionDialog(
                            title = stringResource(R.string.start_delivery_dialog_title),
                            message = stringResource(R.string.start_delivery_dialog_message),
                            rightButtonClick = {
                                viewModel.startDelivery(orderId = orderId)
                            },
                            leftButtonClick = {
                            },
                            onDismiss = {
                                showStartDeliveryDialog = false
                            },
                            rightButtonText = stringResource(R.string.yes),
                            leftButtonText = stringResource(R.string.no)
                        )
                    }

                    // error messages
                    state.errorCancelMessage?.let {
                        scope.launch {
                            snackBarHostState.showSnackbar(it)
                            viewModel.clearCancelErrorMessage()
                        }
                    }

                    state.errorConfirmMessage?.let {
                        scope.launch {
                            snackBarHostState.showSnackbar(it)
                            viewModel.clearConfirmErrorMessage()
                        }
                    }

                    state.errorStartDeliveryMessage?.let {
                        scope.launch {
                            snackBarHostState.showSnackbar(it)
                            viewModel.clearStartDeliveryErrorMessage()
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun OrderDetailsItem(
    modifier: Modifier = Modifier,
    order: Order,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
    isManager: Boolean = false,
    onStartDeliveryClicked: () -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.space_small)),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.space_medium)),
    ) {
        Column(modifier = Modifier.mediumPadding(includeBottom = true)) {
            KeyValue(key = stringResource(R.string.order_id), value = order.id)
            KeyValue(key = stringResource(R.string.recipient), value = order.contactName)
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
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Button(
                        onClick = onCancelClicked,
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    if (isManager) {
                        Button(
                            onClick = onStartDeliveryClicked,
                            colors = ButtonDefaults.buttonColors(Color.Yellow)
                        ) {
                            Text(
                                text = stringResource(R.string.start_delivery),
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            if (order.status == OrderStatus.ON_THE_WAY && !isManager) {
                Button(
                    onClick = onConfirmClicked,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(R.string.confirm_received))
                }
            }
        }
    }
}
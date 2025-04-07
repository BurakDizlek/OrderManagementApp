package com.bd.ordermanagementapp.screens.delivery

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bd.data.model.order.Order
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.details.navigateToOrderDetails
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.KeyValue
import com.bd.ordermanagementapp.ui.components.NotificationPermissionHandler
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.components.filter.OrderFilterComponent
import com.bd.ordermanagementapp.ui.extensions.smallPadding
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.io.IOException

@Composable
fun DeliveryListScreen(
    viewModel: DeliveryViewModel = koinViewModel(),
    navController: NavHostController,
    parentPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getDeliveryOrders()
    }

    OrderManagementAppTheme {
        Scaffold(
            topBar = {
                ToolbarWithTitle(title = stringResource(id = R.string.delivery_list_screen_title))
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                OrderFilterComponent(
                    initialFilter = uiState.filterData,
                    onFilterChanged = { newFilter -> viewModel.onFilterChanged(newFilter) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                when {
                    uiState.loading -> {
                        ProgressView()
                    }

                    uiState.errorMessage != null -> {
                        ErrorView(uiState.errorMessage.orEmpty()) {
                            viewModel.getDeliveryOrders()
                        }
                    }

                    uiState.orders.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.there_are_no_orders))
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = parentPadding.calculateBottomPadding()),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.orders, key = { it.id }) { order ->
                                DeliveryOrderItemView(
                                    order = order,
                                    context = context,
                                    goToDetails = {
                                        navController.navigateToOrderDetails(orderId = order.id)
                                    }
                                )
                            }
                        }
                    }
                }
                NotificationPermissionHandler()
            }
        }

    }
}

@Composable
fun DeliveryOrderItemView(
    order: Order,
    context: Context,
    goToDetails: () -> Unit,
) {
    var address by remember { mutableStateOf<String?>(null) }
    var isLoadingAddress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(order.latitude, order.longitude, address) {
        if (address == null && !isLoadingAddress && (order.latitude != 0.0 || order.longitude != 0.0)) {
            isLoadingAddress = true
            coroutineScope.launch(Dispatchers.IO) {
                val geocoder = Geocoder(context)
                try {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(order.latitude, order.longitude, 1)
                    val resolvedAddress = addresses?.firstOrNull()?.getAddressLine(0)
                        ?: context.getString(R.string.address_not_found)
                    withContext(Dispatchers.Main) { address = resolvedAddress }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        address = context.getString(R.string.address_error)
                    }
                } catch (e: IllegalArgumentException) {
                    withContext(Dispatchers.Main) {
                        address = context.getString(R.string.invalid_coordinates)
                    }
                } finally {
                    withContext(Dispatchers.Main) { isLoadingAddress = false }
                }
            }
        } else if (order.latitude == 0.0 && order.longitude == 0.0) {
            address = context.getString(R.string.invalid_coordinates)
            isLoadingAddress = false
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .smallPadding(),
        onClick = goToDetails
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            KeyValue(key = stringResource(R.string.statuses), value = order.statusText)
            KeyValue(key = stringResource(R.string.recipient), value = order.contactName)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.detected_address_prefix),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                if (isLoadingAddress) {
                    CircularProgressIndicator(
                        modifier = Modifier.run { size(16.dp) },
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        stringResource(id = R.string.address_loading),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(text = address ?: "...", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
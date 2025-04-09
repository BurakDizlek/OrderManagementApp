package com.bd.ordermanagementapp.screens.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bd.data.model.order.OrderStatus
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.details.navigateToOrderDetails
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.components.filter.OrderFilterComponent
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.extensions.mediumPadding
import com.bd.ordermanagementapp.ui.theme.Typography
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DeliveryMapScreen(
    viewModel: DeliveryViewModel,
    navController: NavHostController,
    parentPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()
    val ordersWithLocation = uiState.orders.filter { it.latitude != 0.0 || it.longitude != 0.0 }

    val defaultLocation = LatLng(39.9208, 32.8541) // Ankara
    val initialLatLng = ordersWithLocation.firstOrNull()?.let { LatLng(it.latitude, it.longitude) }
        ?: defaultLocation
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            initialLatLng,
            if (ordersWithLocation.isEmpty()) 10f else 15f
        )
    }
    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = true) }

    Scaffold(
        topBar = { ToolbarWithTitle(title = stringResource(id = R.string.delivery_map_screen_title)) }
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
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    contentPadding = parentPadding,
                    cameraPositionState = cameraPositionState,
                    uiSettings = uiSettings,
                    properties = MapProperties(isMyLocationEnabled = false)
                ) {
                    ordersWithLocation.forEach { order ->
                        Marker(
                            state = MarkerState(position = LatLng(order.latitude, order.longitude)),
                            title = stringResource(R.string.order_id_prefix) + " ${order.id}",
                            snippet = "${stringResource(R.string.recipient_prefix)} ${order.contactName}\n${
                                stringResource(
                                    R.string.status_prefix
                                )
                            } ${order.statusText}",
                            icon = BitmapDescriptorFactory.defaultMarker(order.status.markerHue()),
                            onClick = {
                                navController.navigateToOrderDetails(orderId = order.id)
                                false
                            }
                        )
                    }
                }

                when {
                    uiState.loading -> {
                        ProgressView(modifier = Modifier.align(Alignment.Center))
                    }

                    uiState.errorMessage != null -> {
                        ErrorView(uiState.errorMessage.orEmpty()) {
                            viewModel.getDeliveryOrders()
                        }
                    }

                    ordersWithLocation.isEmpty() -> {
                        Text(
                            stringResource(R.string.no_orders_with_location),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .largePadding(includeBottom = true)
                                .background(color = Color.White)
                                .mediumPadding(includeBottom = true),
                            style = Typography.titleMedium,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

private fun OrderStatus.markerHue(): Float {
    return when (this) {
        OrderStatus.OPEN -> BitmapDescriptorFactory.HUE_GREEN
        OrderStatus.ON_THE_WAY -> BitmapDescriptorFactory.HUE_YELLOW
        OrderStatus.COMPLETED -> BitmapDescriptorFactory.HUE_BLUE
        OrderStatus.REJECTED -> BitmapDescriptorFactory.HUE_RED
        OrderStatus.CANCELED -> BitmapDescriptorFactory.HUE_RED
    }
}
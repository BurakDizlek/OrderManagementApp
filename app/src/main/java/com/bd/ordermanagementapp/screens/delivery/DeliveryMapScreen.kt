package com.bd.ordermanagementapp.screens.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bd.data.model.order.OrderStatus
import com.bd.data.model.order.toName
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.details.navigateToOrderDetails
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.components.filter.OrderFilterComponent
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.extensions.mediumPadding
import com.bd.ordermanagementapp.ui.extensions.smallPadding
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

    var selectedMarkerOrderId by remember { mutableStateOf<String?>(null) }

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
                    properties = MapProperties(isMyLocationEnabled = false),
                    onMapClick = { selectedMarkerOrderId = null },
                    onMapLongClick = { selectedMarkerOrderId = null }
                ) {
                    ordersWithLocation.forEach { order ->
                        Marker(
                            state = MarkerState(position = LatLng(order.latitude, order.longitude)),
                            title = "${stringResource(R.string.recipient)}: ${order.contactName}",
                            snippet = "${stringResource(R.string.content)}: ${order.content}",
                            icon = BitmapDescriptorFactory.defaultMarker(order.status.markerHue()),
                            onClick = {
                                val currentOrderId = order.id
                                if (selectedMarkerOrderId == currentOrderId) {
                                    navController.navigateToOrderDetails(orderId = currentOrderId)
                                    true
                                } else {
                                    selectedMarkerOrderId = currentOrderId
                                    false
                                }
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

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            start = dimensionResource(R.dimen.space_medium),
                            bottom = parentPadding.calculateBottomPadding() + dimensionResource(R.dimen.space_large),
                        )
                        .background(
                            color = Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(bottom = dimensionResource(R.dimen.space_small))
                ) {
                    uiState.filterData.statuses?.forEach {
                        Row(
                            modifier = Modifier.smallPadding(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(color = it.toComposeColor(), shape = CircleShape)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = it.toName(),
                                style = Typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
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
        OrderStatus.REJECTED -> BitmapDescriptorFactory.HUE_ORANGE
        OrderStatus.CANCELED -> BitmapDescriptorFactory.HUE_RED
    }
}

fun OrderStatus.toComposeColor(
    saturation: Float = 1.0f,
    value: Float = 1.0f,
): Color {
    val hue = this.markerHue()
    return Color.hsv(hue = hue, saturation = saturation, value = value)
}
package com.bd.ordermanagementapp.screens.orders.create.location

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bd.data.extensions.orZero
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPickerScreen(
    viewModel: LocationPickerViewModel = koinViewModel(),
    navController: NavController,
    data: CreateOrderRoute.Starter,
) {
    val context = LocalContext.current
    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val pickedLocation by viewModel.pickedLocation

    LaunchedEffect(locationPermissionState.status.isGranted) {
        viewModel.initialize(context)
        if (locationPermissionState.status.isGranted) {
            viewModel.setPickedLocationWithCurrent()
        }
    }

    Scaffold(
        topBar = {
            ToolbarWithTitle(
                stringResource(R.string.location_picker_title),
                navigationControllerToPopBack = navController
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (locationPermissionState.status.isGranted) {
                    GoogleMap(
                        modifier = Modifier.weight(1f),
                        cameraPositionState = viewModel.cameraPositionState.value,
                        onMapClick = { latLng -> viewModel.onMapClick(latLng) }
                    ) {
                        pickedLocation?.let {
                            Marker(
                                state = MarkerState(position = it),
                                title = stringResource(R.string.location_picker_marker_text)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.space_medium)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),

                            onClick = {
                                if (locationPermissionState.status.isGranted) {
                                    viewModel.setPickedLocationWithCurrent()
                                }
                            }) {
                            Text(stringResource(R.string.current_location))
                        }
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            onClick = {
                                val confirmedLocation = viewModel.confirmLocation()
                                navController.navigate(
                                    CreateOrderRoute.DetailEntry(
                                        isQuickOrder = data.isQuickOrder,
                                        menuItemId = data.menuItemId,
                                        longitude = confirmedLocation?.longitude.orZero(),
                                        latitude = confirmedLocation?.latitude.orZero()
                                    )
                                )
                            }) {
                            Text(stringResource(R.string.confirm))
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val textToShow =
                            if (locationPermissionState.status.shouldShowRationale) {
                                stringResource(R.string.location_permission_needed_to_send_delivery)
                            } else {
                                stringResource(R.string.location_permission_denied_message)
                            }
                        Text(textToShow)
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_small)))
                        Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                            Text(stringResource(R.string.request_permission))
                        }
                    }
                }
            }
        }
    )
}
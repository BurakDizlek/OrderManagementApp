package com.bd.ordermanagementapp.screens.orders.create

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LocationPickerViewModel : ViewModel() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val _pickedLocation = mutableStateOf<LatLng?>(null)
    val pickedLocation: State<LatLng?> = _pickedLocation

    private val _cameraPositionState = mutableStateOf(CameraPositionState())
    val cameraPositionState: State<CameraPositionState> = _cameraPositionState

    private val _locationPermissionGranted = mutableStateOf(false)
    val locationPermissionGranted: State<Boolean> = _locationPermissionGranted

    fun initialize(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        checkLocationPermission(context)
    }

    private fun checkLocationPermission(context: Context) {
        _locationPermissionGranted.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun setPickedLocationWithCurrent() {
        viewModelScope.launch {
            if (locationPermissionGranted.value) {
                try {
                    val location: Location? = fusedLocationClient.lastLocation.await()
                    location?.let {
                        _pickedLocation.value = LatLng(it.latitude, it.longitude)
                        _cameraPositionState.value.position = CameraPosition.fromLatLngZoom(
                            LatLng(it.latitude, it.longitude), 15f
                        )
                    }
                } catch (e: Exception) {
                    // Handle location retrieval error
                }
            }
        }
    }

    fun onMapClick(latLng: LatLng) {
        _pickedLocation.value = latLng
    }

    fun confirmLocation(): LatLng? {
        return _pickedLocation.value
    }
}
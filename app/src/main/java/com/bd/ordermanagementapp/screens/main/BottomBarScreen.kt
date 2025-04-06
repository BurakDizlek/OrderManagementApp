package com.bd.ordermanagementapp.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.bd.ordermanagementapp.R

sealed class BottomBarScreen(
    val route: String,
    val titleId: Int,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        titleId = R.string.home_bottom_bar_title,
        icon = Icons.Default.Home
    )

    object Cart : BottomBarScreen(
        route = "cart",
        titleId = R.string.cart_bottom_bar_title,
        icon = Icons.Default.ShoppingCart
    )

    object Orders : BottomBarScreen(
        route = "orders",
        titleId = R.string.orders_bottom_bar_title,
        icon = Icons.Default.LocationOn
    )

    object DeliveryList : BottomBarScreen(
        route = "delivery_list",
        titleId = R.string.delivery_list_bottom_bar_title,
        icon = Icons.AutoMirrored.Default.List
    )

    object DeliveryMap : BottomBarScreen(
        route = "delivery_map",
        titleId = R.string.delivery_map_bottom_bar_title,
        icon = Icons.Default.Map
    )
}
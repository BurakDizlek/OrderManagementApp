package com.bd.ordermanagementapp.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
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

    object Delivery : BottomBarScreen(
        route = "delivery",
        titleId = R.string.delivery_bottom_bar_title,
        icon = Icons.Default.LocationOn
    )
}
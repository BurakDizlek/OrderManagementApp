package com.bd.ordermanagementapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bd.ordermanagementapp.screens.cart.CartScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryScreen
import com.bd.ordermanagementapp.screens.home.HomeScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController, startDestinationRoute: String, padding: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDestinationRoute) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(padding = padding)
        }

        composable(route = BottomBarScreen.Cart.route) {
            CartScreen()
        }

        composable(route = BottomBarScreen.Delivery.route) {
            DeliveryScreen()
        }
    }
}
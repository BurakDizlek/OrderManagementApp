package com.bd.ordermanagementapp.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bd.ordermanagementapp.screens.cart.CartScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryScreen
import com.bd.ordermanagementapp.screens.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController, startDestinationRoute: String, padding: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDestinationRoute) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(padding = padding)
        }

        composable(route = BottomBarScreen.Cart.route) {
            CartScreen(padding = padding)
        }

        composable(route = BottomBarScreen.Orders.route) {
            DeliveryScreen()//todo
        }

        composable(route = BottomBarScreen.Delivery.route) {
            DeliveryScreen()
        }
    }
}
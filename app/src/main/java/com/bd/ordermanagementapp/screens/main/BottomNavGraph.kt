package com.bd.ordermanagementapp.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bd.ordermanagementapp.screens.Graphs
import com.bd.ordermanagementapp.screens.cart.CartScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryScreen
import com.bd.ordermanagementapp.screens.home.HomeScreen
import com.bd.ordermanagementapp.screens.login.loginNavigationGraph

@Composable
fun BottomNavGraph(
    navController: NavHostController, startDestinationRoute: String, padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
        route = Graphs.BOTTOM_BAR
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navigationController = navController, padding = padding)
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
        loginNavigationGraph(navController)
    }
}
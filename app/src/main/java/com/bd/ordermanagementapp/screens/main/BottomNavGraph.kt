package com.bd.ordermanagementapp.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bd.ordermanagementapp.screens.cart.CartScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryScreen
import com.bd.ordermanagementapp.screens.home.HomeScreen
import com.bd.ordermanagementapp.screens.home.campaign.campaignDetailsNavigationGraph
import com.bd.ordermanagementapp.screens.login.loginNavigationGraph
import com.bd.ordermanagementapp.screens.orders.create.createOrderNavigationGraph
import com.bd.ordermanagementapp.screens.orders.list.OrdersScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController, startDestinationRoute: String, padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
        route = GraphRoute.BOTTOM_BAR
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController, padding = padding)
        }

        composable(route = BottomBarScreen.Cart.route) {
            CartScreen(padding = padding)
        }

        composable(route = BottomBarScreen.Orders.route) {
            OrdersScreen(navController = navController)
        }

        composable(route = BottomBarScreen.Delivery.route) {
            DeliveryScreen()
        }
        loginNavigationGraph(navController)

        createOrderNavigationGraph(navController)

        campaignDetailsNavigationGraph(navController)
    }
}
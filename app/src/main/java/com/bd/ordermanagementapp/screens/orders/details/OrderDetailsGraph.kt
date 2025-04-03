package com.bd.ordermanagementapp.screens.orders.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bd.ordermanagementapp.screens.main.GraphRoute

fun NavGraphBuilder.orderDetailsNavigationGraph(navController: NavHostController) {
    navigation(
        startDestination = "order_details/{orderId}", route = GraphRoute.ORDER_DETAILS
    ) {
        composable("order_details/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId").orEmpty()
            OrderDetailsScreen(orderId = orderId, navController = navController)
        }
    }
}

fun NavHostController.navigateToOrderDetails(orderId: String) {
    navigate("order_details/$orderId")
}
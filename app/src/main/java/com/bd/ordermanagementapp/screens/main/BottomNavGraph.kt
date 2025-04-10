package com.bd.ordermanagementapp.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bd.ordermanagementapp.screens.cart.CartScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryListScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryMapScreen
import com.bd.ordermanagementapp.screens.delivery.DeliveryViewModel
import com.bd.ordermanagementapp.screens.home.HomeScreen
import com.bd.ordermanagementapp.screens.home.campaign.campaignDetailsNavigationGraph
import com.bd.ordermanagementapp.screens.login.loginNavigationGraph
import com.bd.ordermanagementapp.screens.orders.create.createOrderNavigationGraph
import com.bd.ordermanagementapp.screens.orders.details.orderDetailsNavigationGraph
import com.bd.ordermanagementapp.screens.orders.list.OrdersScreen
import com.bd.ordermanagementapp.screens.profile.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController, startDestinationRoute: String, padding: PaddingValues,
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
            CartScreen(navController = navController, padding = padding)
        }

        composable(route = BottomBarScreen.Orders.route) {
            OrdersScreen(navController = navController, parentPadding = padding)
        }

        composable(route = BottomBarScreen.DeliveryList.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(GraphRoute.BOTTOM_BAR)
            }
            val viewModel: DeliveryViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            DeliveryListScreen(
                viewModel = viewModel,
                navController = navController,
                parentPadding = padding
            )
        }
        composable(route = BottomBarScreen.DeliveryMap.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(GraphRoute.BOTTOM_BAR)
            }
            val viewModel: DeliveryViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            DeliveryMapScreen(
                viewModel = viewModel,
                navController = navController,
                parentPadding = padding
            )
        }

        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        loginNavigationGraph(navController)

        createOrderNavigationGraph(navController)

        campaignDetailsNavigationGraph(navController)

        orderDetailsNavigationGraph(navController)
    }
}
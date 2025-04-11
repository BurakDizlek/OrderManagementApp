package com.bd.ordermanagementapp.screens.orders.create

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bd.ordermanagementapp.screens.orders.create.detail.OrderDetailEntryScreen
import com.bd.ordermanagementapp.screens.orders.create.location.LocationPickerScreen
import com.bd.ordermanagementapp.screens.orders.create.success.CreateOrderSuccessScreen


fun NavGraphBuilder.createOrderNavigationGraph(
    navController: NavHostController,
) { //consider having navigation.
    composable<CreateOrderRoute.Starter> { navBackStackEntry ->
        LocationPickerScreen(navController = navController, data = navBackStackEntry.toRoute())
    }

    composable<CreateOrderRoute.DetailEntry> {
        OrderDetailEntryScreen(navController = navController)
    }

    composable<CreateOrderRoute.Success> {
        CreateOrderSuccessScreen(data = it.toRoute(), navController = navController)
    }
}
package com.bd.ordermanagementapp.screens.orders.create

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bd.ordermanagementapp.screens.orders.create.detail.OrderDetailEntryData
import com.bd.ordermanagementapp.screens.orders.create.detail.OrderDetailEntryScreen
import com.bd.ordermanagementapp.screens.orders.create.location.LocationPickerScreen
import com.bd.ordermanagementapp.screens.orders.create.location.LocationPickerScreenData


fun NavGraphBuilder.createOrderNavigationGraph(
    navController: NavHostController,
) { //consider having navigation.
    composable<LocationPickerScreenData> { navBackStackEntry ->
        LocationPickerScreen(navController = navController, data = navBackStackEntry.toRoute())
    }

    composable<OrderDetailEntryData> {
        OrderDetailEntryScreen(navController = navController)
    }
}
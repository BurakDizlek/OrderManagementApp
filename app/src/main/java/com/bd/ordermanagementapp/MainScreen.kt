package com.bd.ordermanagementapp

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bd.ordermanagementapp.data.AppDataProvider
import com.bd.ordermanagementapp.data.CustomerType

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) }) {
        BottomNavGraph(
            navController = navController,
            startDestinationRoute = getStartDestinationRoute(),
            padding = it
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = getBottomBarOptions()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen, currentDestination: NavDestination?, navController: NavController
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = screen.icon, contentDescription = screen.title
            )
        },
        label = {
            Text(text = screen.title)
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}


private fun getBottomBarOptions(): List<BottomBarScreen> { //move to viewmodel
    return when (AppDataProvider.currentCustomerType) {
        CustomerType.RESTAURANT_MANAGER -> {
            listOf()
        }

        else -> { // NOT_DEFINED or CUSTOMER
            listOf(
                BottomBarScreen.Home, BottomBarScreen.Cart, BottomBarScreen.Delivery
            )
        }
    }
}

private fun getStartDestinationRoute(): String {
    return when (AppDataProvider.currentCustomerType) {
        CustomerType.RESTAURANT_MANAGER -> {
            BottomBarScreen.Home.route //TODO
        }

        else -> {
            BottomBarScreen.Home.route
        }
    }
}
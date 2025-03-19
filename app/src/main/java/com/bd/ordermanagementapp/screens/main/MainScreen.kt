package com.bd.ordermanagementapp.screens.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController, state = state) }) { padding ->
        BottomNavGraph(
            navController = navController,
            startDestinationRoute = state.bottomBarStartDestinationRoute,
            padding = padding
        )
    }
}

@Composable
fun BottomBar(navController: NavController, state: MainUiViewState) {
    val screens = state.bottomBarScreens
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isBottomBarVisible = screens.any { it.screen.route == currentDestination?.route }
    if (isBottomBarVisible) {
        NavigationBar {
            screens.forEach { screen ->
                if (screen.isVisible) {
                    AddItem(
                        screen = screen.screen,
                        currentDestination = currentDestination,
                        navController = navController,
                        badgeCount = screen.badgeCount
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController,
    badgeCount: Int?
) {
    NavigationBarItem(
        icon = {
            if (badgeCount != null && badgeCount > 0) {
                BadgedBox(badge = { Badge { Text(badgeCount.toString()) } }) {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(screen.titleId)
                    )
                }
            } else {
                Icon(
                    imageVector = screen.icon, contentDescription = stringResource(screen.titleId)
                )
            }
        },
        label = {
            Text(text = stringResource(screen.titleId))
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
package com.bd.ordermanagementapp.screens.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bd.ordermanagementapp.screens.Graphs


fun NavGraphBuilder.loginNavigationGraph(
    navigationController: NavHostController,
) {
    navigation(
        route = Graphs.LOGIN,
        startDestination = "login"
    ) {
        composable(route = "login") {
            LoginScreen(navigationController = navigationController)
        }
    }
}
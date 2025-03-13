package com.bd.ordermanagementapp.screens.main

data class MainUiViewState(
    val bottomBarScreens: List<BottomNavItem> = listOf(),
    val bottomBarStartDestinationRoute: String = BottomBarScreen.Home.route
)
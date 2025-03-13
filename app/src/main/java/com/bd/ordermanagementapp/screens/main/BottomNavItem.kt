package com.bd.ordermanagementapp.screens.main

data class BottomNavItem(
    val screen: BottomBarScreen,
    val isVisible: Boolean,
    val badgeCount: Int? = null
)
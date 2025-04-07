package com.bd.ordermanagementapp.data.manager

import com.bd.core.session.UserType
import com.bd.ordermanagementapp.screens.main.BottomNavItem
import kotlinx.coroutines.flow.SharedFlow

interface UserBottomBarManager {
    val bottomBarOptionsFlow: SharedFlow<List<BottomNavItem>>
    val bottomBarStartDestinationRoute: SharedFlow<String>
    val currentUserType: SharedFlow<UserType>
    suspend fun onCartCountChanged(cartCount: Int)
    suspend fun onUserTypeChanged(userType: UserType)
    suspend fun updateBottomBar()
}
package com.bd.ordermanagementapp.data.repository

import com.bd.ordermanagementapp.data.CustomerType
import com.bd.ordermanagementapp.screens.main.BottomNavItem
import kotlinx.coroutines.flow.SharedFlow

interface BottomBarRepository {
    val bottomBarOptionsFlow: SharedFlow<List<BottomNavItem>>
    val bottomBarStartDestinationRoute: SharedFlow<String>
    suspend fun onCartCountChanged(cartCount: Int)
    suspend fun onCustomerTypeChanged(customerType: CustomerType)
    suspend fun updateBottomBar()
}
package com.bd.ordermanagementapp.data.repository

import com.bd.ordermanagementapp.data.CustomerType
import com.bd.ordermanagementapp.screens.main.BottomBarScreen
import com.bd.ordermanagementapp.screens.main.BottomNavItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class BottomBarRepositoryImpl : BottomBarRepository {
    private var customerType: CustomerType = CustomerType.NOT_DEFINED
    private var cartCount: Int = 0

    private val _bottomBarOptionsFlow = MutableSharedFlow<List<BottomNavItem>>(replay = 1)
    override val bottomBarOptionsFlow: SharedFlow<List<BottomNavItem>> =
        _bottomBarOptionsFlow.asSharedFlow()

    private val _bottomBarStartDestinationRoute = MutableSharedFlow<String>(replay = 1)
    override val bottomBarStartDestinationRoute: SharedFlow<String> =
        _bottomBarStartDestinationRoute.asSharedFlow()

    private fun getStartDestinationRoute(): String {
        return when (customerType) {
            CustomerType.RESTAURANT_MANAGER -> {
                BottomBarScreen.Delivery.route
            }

            else -> {
                BottomBarScreen.Home.route
            }
        }
    }

    private fun getBottomBarOptions(): List<BottomNavItem> {
        val homeVisibility = customerType != CustomerType.RESTAURANT_MANAGER
        val cartVisibility = customerType != CustomerType.RESTAURANT_MANAGER
        val ordersVisibility = customerType != CustomerType.RESTAURANT_MANAGER
        val deliveryVisibility = customerType == CustomerType.RESTAURANT_MANAGER

        return listOf(
            BottomNavItem(BottomBarScreen.Home, homeVisibility),
            BottomNavItem(BottomBarScreen.Cart, cartVisibility, cartCount),
            BottomNavItem(BottomBarScreen.Orders, ordersVisibility),
            BottomNavItem(BottomBarScreen.Delivery, deliveryVisibility)
        )
    }

    override suspend fun onCartCountChanged(cartCount: Int) {
        this.cartCount = cartCount
        _bottomBarOptionsFlow.emit(getBottomBarOptions())
    }

    override suspend fun onCustomerTypeChanged(customerType: CustomerType) {
        this.customerType = customerType
        updateBottomBar()
    }

    override suspend fun updateBottomBar() {
        _bottomBarStartDestinationRoute.emit(getStartDestinationRoute())
        _bottomBarOptionsFlow.emit(getBottomBarOptions())
    }
}
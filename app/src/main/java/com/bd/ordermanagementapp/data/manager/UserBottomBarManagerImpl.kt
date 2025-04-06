package com.bd.ordermanagementapp.data.manager

import com.bd.core.session.SessionManager
import com.bd.core.session.UserType
import com.bd.ordermanagementapp.screens.main.BottomBarScreen
import com.bd.ordermanagementapp.screens.main.BottomNavItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class UserBottomBarManagerImpl(sessionManager: SessionManager) : UserBottomBarManager {
    private var userType: UserType = sessionManager.getUserType()
    private var cartCount: Int = 0

    private val _bottomBarOptionsFlow = MutableSharedFlow<List<BottomNavItem>>(replay = 1)
    override val bottomBarOptionsFlow: SharedFlow<List<BottomNavItem>> =
        _bottomBarOptionsFlow.asSharedFlow()

    private val _bottomBarStartDestinationRoute = MutableSharedFlow<String>(replay = 1)
    override val bottomBarStartDestinationRoute: SharedFlow<String> =
        _bottomBarStartDestinationRoute.asSharedFlow()

    private fun getStartDestinationRoute(): String {
        return when (userType) {
            UserType.RESTAURANT_MANAGER -> {
                BottomBarScreen.DeliveryList.route
            }

            else -> {
                BottomBarScreen.Home.route
            }
        }
    }

    private fun getBottomBarOptions(): List<BottomNavItem> {
        val homeVisibility = userType != UserType.RESTAURANT_MANAGER
        val cartVisibility = userType != UserType.RESTAURANT_MANAGER
        val ordersVisibility = userType == UserType.CUSTOMER
        val deliveryVisibility = userType == UserType.RESTAURANT_MANAGER

        return listOf(
            BottomNavItem(BottomBarScreen.Home, homeVisibility),
            BottomNavItem(BottomBarScreen.Cart, cartVisibility, cartCount),
            BottomNavItem(BottomBarScreen.Orders, ordersVisibility),
            BottomNavItem(BottomBarScreen.DeliveryList, deliveryVisibility),
            BottomNavItem(BottomBarScreen.DeliveryMap, deliveryVisibility)
        )
    }

    override suspend fun onCartCountChanged(cartCount: Int) {
        this.cartCount = cartCount
        _bottomBarOptionsFlow.emit(getBottomBarOptions())
    }

    override suspend fun onUserTypeChanged(userType: UserType) {
        this.userType = userType
        updateBottomBar()
    }

    override suspend fun updateBottomBar() {
        _bottomBarStartDestinationRoute.emit(getStartDestinationRoute())
        _bottomBarOptionsFlow.emit(getBottomBarOptions())
    }
}
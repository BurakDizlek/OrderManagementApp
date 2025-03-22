package com.bd.network.di

import com.bd.network.AppHttpClient
import com.bd.network.HttpLogger
import com.bd.network.service.campaign.CampaignService
import com.bd.network.service.campaign.CampaignServiceImpl
import com.bd.network.service.cart.CartService
import com.bd.network.service.cart.CartServiceImpl
import com.bd.network.service.login.LoginService
import com.bd.network.service.login.LoginServiceImpl
import com.bd.network.service.menu.MenuService
import com.bd.network.service.menu.MenuServiceImpl
import com.bd.network.service.order.OrderService
import com.bd.network.service.order.OrderServiceImpl
import org.koin.dsl.module

val networkModule = module {
    single { HttpLogger() }
    single { AppHttpClient(get(), get()).create() }
    single<CampaignService> { CampaignServiceImpl(get()) }
    single<MenuService> { MenuServiceImpl(get()) }
    single<CartService> { CartServiceImpl(get()) }
    single<LoginService> { LoginServiceImpl(get()) }
    single<OrderService> { OrderServiceImpl(get()) }
}
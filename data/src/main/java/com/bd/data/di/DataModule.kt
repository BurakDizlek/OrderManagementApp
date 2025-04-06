package com.bd.data.di

import com.bd.data.repository.campaign.CampaignRepository
import com.bd.data.repository.campaign.CampaignRepositoryImpl
import com.bd.data.repository.cart.CartRepository
import com.bd.data.repository.cart.CartRepositoryImpl
import com.bd.data.repository.geocoding.GeocodingRepository
import com.bd.data.repository.geocoding.GeocodingRepositoryImpl
import com.bd.data.repository.login.LoginRepository
import com.bd.data.repository.login.LoginRepositoryImpl
import com.bd.data.repository.menu.MenuRepository
import com.bd.data.repository.menu.MenuRepositoryImpl
import com.bd.data.repository.order.OrderRepository
import com.bd.data.repository.order.OrderRepositoryImpl
import com.bd.data.usecase.AddToCartUseCase
import com.bd.network.di.networkModule
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule)
    single<CampaignRepository> { CampaignRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single { AddToCartUseCase(get()) }
    single<GeocodingRepository> { GeocodingRepositoryImpl(get()) }
}
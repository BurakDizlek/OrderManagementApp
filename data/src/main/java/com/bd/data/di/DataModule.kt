package com.bd.data.di

import com.bd.data.repository.campaign.CampaignRepository
import com.bd.data.repository.campaign.CampaignRepositoryImpl
import com.bd.data.repository.cart.CartRepository
import com.bd.data.repository.cart.CartRepositoryImpl
import com.bd.data.repository.menu.MenuRepository
import com.bd.data.repository.menu.MenuRepositoryImpl
import com.bd.network.di.networkModule
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule)
    single<CampaignRepository> { CampaignRepositoryImpl(get()) }
    single<MenuRepository> { MenuRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }
}
package com.bd.ordermanagementapp.di

import com.bd.ordermanagementapp.MainActivityViewModel
import com.bd.ordermanagementapp.screens.cart.CartViewModel
import com.bd.ordermanagementapp.screens.delivery.DeliveryViewModel
import com.bd.ordermanagementapp.screens.home.HomeViewModel
import com.bd.ordermanagementapp.screens.home.campaign.CampaignDetailsViewModel
import com.bd.ordermanagementapp.screens.login.LoginViewModel
import com.bd.ordermanagementapp.screens.main.MainViewModel
import com.bd.ordermanagementapp.screens.orders.create.detail.OrderDetailEntryViewModel
import com.bd.ordermanagementapp.screens.orders.create.location.LocationPickerViewModel
import com.bd.ordermanagementapp.screens.orders.details.OrderDetailsViewModel
import com.bd.ordermanagementapp.screens.orders.list.OrdersViewModel
import com.bd.ordermanagementapp.screens.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::LocationPickerViewModel)
    viewModelOf(::OrderDetailEntryViewModel)
    viewModelOf(::CampaignDetailsViewModel)
    viewModelOf(::OrdersViewModel)
    viewModelOf(::OrderDetailsViewModel)
    viewModelOf(::DeliveryViewModel)
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::ProfileViewModel)
}
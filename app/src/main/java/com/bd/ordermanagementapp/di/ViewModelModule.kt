package com.bd.ordermanagementapp.di

import com.bd.ordermanagementapp.screens.cart.CartViewModel
import com.bd.ordermanagementapp.screens.home.HomeViewModel
import com.bd.ordermanagementapp.screens.login.LoginViewModel
import com.bd.ordermanagementapp.screens.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
}
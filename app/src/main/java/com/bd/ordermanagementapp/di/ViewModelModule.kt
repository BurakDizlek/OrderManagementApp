package com.bd.ordermanagementapp.di

import com.bd.ordermanagementapp.screens.cart.CartViewModel
import com.bd.ordermanagementapp.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { CartViewModel(get()) }
}
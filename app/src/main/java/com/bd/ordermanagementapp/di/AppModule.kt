package com.bd.ordermanagementapp.di

import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule)
}
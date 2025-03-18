package com.bd.ordermanagementapp.di

import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import com.bd.ordermanagementapp.data.manager.UserBottomBarManagerImpl
import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule)
    single<UserBottomBarManager> { UserBottomBarManagerImpl(get()) }
}
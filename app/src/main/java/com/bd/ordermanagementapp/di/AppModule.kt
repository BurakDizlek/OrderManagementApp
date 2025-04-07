package com.bd.ordermanagementapp.di

import com.bd.ordermanagementapp.data.manager.UserBottomBarManager
import com.bd.ordermanagementapp.data.manager.UserBottomBarManagerImpl
import com.bd.ordermanagementapp.data.notification.NotificationDataProvider
import com.bd.ordermanagementapp.data.notification.NotificationDataProviderImpl
import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule)
    single<UserBottomBarManager> { UserBottomBarManagerImpl(get()) }
    single<NotificationDataProvider> { NotificationDataProviderImpl(get()) }
}
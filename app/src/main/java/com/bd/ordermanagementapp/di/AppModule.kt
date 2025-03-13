package com.bd.ordermanagementapp.di

import com.bd.ordermanagementapp.data.repository.BottomBarRepository
import com.bd.ordermanagementapp.data.repository.BottomBarRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule)
    single<BottomBarRepository> { BottomBarRepositoryImpl() }
}
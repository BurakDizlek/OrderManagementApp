package com.bd.data.di

import com.bd.data.repository.CampaignRepository
import com.bd.data.repository.CampaignRepositoryImpl
import com.bd.network.di.networkModule
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule)
    single<CampaignRepository> { CampaignRepositoryImpl(get()) }
}
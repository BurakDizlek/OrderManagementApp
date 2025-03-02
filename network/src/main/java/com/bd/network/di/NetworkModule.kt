package com.bd.network.di

import com.bd.network.AppHttpClient
import com.bd.network.service.campaign.CampaignService
import com.bd.network.service.campaign.CampaignServiceImpl
import org.koin.dsl.module

val networkModule = module {
    single { AppHttpClient().create() }
    single<CampaignService> { CampaignServiceImpl(get()) }
}
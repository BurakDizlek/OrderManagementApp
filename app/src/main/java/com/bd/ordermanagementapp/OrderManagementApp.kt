package com.bd.ordermanagementapp

import android.app.Application
import com.bd.core.di.coreModule
import com.bd.data.di.dataModule
import com.bd.ordermanagementapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class OrderManagementApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@OrderManagementApp)
            modules(appModule, dataModule, coreModule)
        }
    }

}
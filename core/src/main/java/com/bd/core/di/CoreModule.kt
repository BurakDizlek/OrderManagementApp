package com.bd.core.di

import com.bd.core.prefs.SecurePreferences
import com.bd.core.session.SessionManager
import com.bd.core.session.SessionManagerImpl
import org.koin.dsl.module

val coreModule = module {
    single { SecurePreferences(get()) }
    single<SessionManager> { SessionManagerImpl(get()) }
}
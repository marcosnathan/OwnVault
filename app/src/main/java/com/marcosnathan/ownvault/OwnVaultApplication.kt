package com.marcosnathan.ownvault

import android.app.Application
import com.marcosnathan.ownvault.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinApplication
import org.koin.core.context.startKoin
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(
    modules = [AppModules::class]
)
class OwnVaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin<OwnVaultApplication> {
            androidContext(this@OwnVaultApplication)
            androidLogger()
        }
    }
}
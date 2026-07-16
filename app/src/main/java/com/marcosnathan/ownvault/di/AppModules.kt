package com.marcosnathan.ownvault.di

import com.marcosnathan.ownvault.data.datasource.local.database.di.DaosModule
import com.marcosnathan.ownvault.data.datasource.local.database.di.DatabaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        DatabaseModule::class,
        DaosModule::class
    ]
)
@ComponentScan("com.marcosnathan.ownvault")
class AppModules
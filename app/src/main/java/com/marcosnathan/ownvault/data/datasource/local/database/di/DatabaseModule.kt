package com.marcosnathan.ownvault.data.datasource.local.database.di

import android.content.Context
import androidx.room.Room
import com.marcosnathan.ownvault.data.datasource.local.database.OwnVaultDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DatabaseModule {

    @Single
    fun providesDatabase(
        context: Context
    ) : OwnVaultDatabase = Room.databaseBuilder(
        context,
        OwnVaultDatabase::class.java,
        "own_vault.db"
    ).build()
}
package com.marcosnathan.ownvault.data.datasource.local.database.di

import com.marcosnathan.ownvault.data.datasource.local.database.OwnVaultDatabase
import com.marcosnathan.ownvault.data.datasource.local.database.dao.EncryptedFileDao
import com.marcosnathan.ownvault.data.datasource.local.database.dao.FolderDao
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DaosModule{

    @Single
    fun providesFolderDao(
        database: OwnVaultDatabase
    ) : FolderDao = database.folderDao()

    @Single
    fun providesEncryptedFileDao(
        database: OwnVaultDatabase
    ) : EncryptedFileDao = database.encryptedFileDao()

}

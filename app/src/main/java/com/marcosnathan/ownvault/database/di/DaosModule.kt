package com.marcosnathan.ownvault.database.di

import com.marcosnathan.ownvault.database.OwnVaultDatabase
import com.marcosnathan.ownvault.database.dao.EncryptedFileDao
import com.marcosnathan.ownvault.database.dao.FolderDao
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

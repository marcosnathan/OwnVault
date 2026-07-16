package com.marcosnathan.ownvault.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcosnathan.ownvault.data.datasource.local.database.converters.InstantConverter
import com.marcosnathan.ownvault.data.datasource.local.database.dao.EncryptedFileDao
import com.marcosnathan.ownvault.data.datasource.local.database.dao.FolderDao
import com.marcosnathan.ownvault.data.datasource.local.database.model.EncryptedFileEntity
import com.marcosnathan.ownvault.data.datasource.local.database.model.FolderEntity

@Database(
    entities = [FolderEntity::class, EncryptedFileEntity::class],
    version = 1,
)
@TypeConverters(
    InstantConverter::class
)
abstract class OwnVaultDatabase : RoomDatabase() {
    abstract fun folderDao() : FolderDao

    abstract fun encryptedFileDao() : EncryptedFileDao
}
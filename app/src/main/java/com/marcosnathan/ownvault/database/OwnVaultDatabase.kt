package com.marcosnathan.ownvault.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcosnathan.ownvault.database.converters.InstantConverter
import com.marcosnathan.ownvault.database.dao.EncryptedFileDao
import com.marcosnathan.ownvault.database.dao.FolderDao
import com.marcosnathan.ownvault.database.model.EncryptedFileEntity
import com.marcosnathan.ownvault.database.model.FolderEntity

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
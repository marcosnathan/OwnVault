package com.marcosnathan.ownvault

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.marcosnathan.ownvault.database.OwnVaultDatabase
import com.marcosnathan.ownvault.database.dao.EncryptedFileDao
import com.marcosnathan.ownvault.database.dao.FolderDao
import org.junit.After
import org.junit.Before

abstract class DatabaseTest {

    private lateinit var ownVaultDatabase: OwnVaultDatabase
    protected lateinit var folderDao: FolderDao

    protected lateinit var encryptedFileDao: EncryptedFileDao

    @Before
    fun createDb(){
        ownVaultDatabase = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context = context,
                OwnVaultDatabase::class.java
            ).allowMainThreadQueries().build()
        }
        folderDao = ownVaultDatabase.folderDao()
        encryptedFileDao = ownVaultDatabase.encryptedFileDao()
    }

    @After
    fun tearDown() {
        ownVaultDatabase.close()
    }

}
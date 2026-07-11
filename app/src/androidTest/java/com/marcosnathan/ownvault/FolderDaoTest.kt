package com.marcosnathan.ownvault

import android.database.sqlite.SQLiteConstraintException
import com.marcosnathan.ownvault.database.model.FolderEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.days

class FolderDaoTest : DatabaseTest() {
    private val folder1 = FolderEntity(
        id = 1L,
        name = "Folder 1"
    )

    private val folder2 = FolderEntity(
        id = 2L,
        name = "Folder 2"
    )

    @Test
    fun folderDao_insertFolder_folderSaved() = runTest {
        folderDao.insert(folder1)
        val allFolders = folderDao.getAll().first()
        assertEquals(1, allFolders.size)
        assertEquals(folder1, allFolders[0].folder.copy(
            createdAt = folder1.createdAt
        ))

    }

    @Test
    fun folderDao_deleteFolder_folderDeleted() = runTest {
        folderDao.insert(folder1)
        folderDao.delete(folder1)
        val allFolders = folderDao.getAll().first()
        assertEquals(0, allFolders.size)
    }

    @Test
    fun folderDao_updateFolder_folderUpdated() = runTest {
        folderDao.insert(folder1)
        folderDao.update(
            folder1.copy(
                name = "New Folder 1"
            )
        )
        val allFolders = folderDao.getAll().first()
        assertEquals(1, allFolders.size)
        assertEquals("New Folder 1", allFolders[0].folder.name)
    }

    @Test
    fun folderDao_foldersSaved_areFoldersOrderedByNameDesc() = runTest {
        folderDao.insert(folder2)
        folderDao.insert(folder1)
        val allFolders = folderDao.getAll().first()
        assertEquals(folder1, allFolders[0].folder.copy(
            createdAt = folder1.createdAt
        ))
        assertEquals(folder2, allFolders[1].folder.copy(
            createdAt = folder2.createdAt
        ))
    }

    @Test
    fun folderDao_foldersSaved_areFoldersOrderedByCreatedAtDesc() = runTest {
        val recentFolder = folder1
        folderDao.insert(
            recentFolder
        )

        val oldestFolder = folder2.copy(
            createdAt = folder2.createdAt.minus(1.days)
        )
        folderDao.insert(
            oldestFolder
        )

        val allFolders = folderDao.getAll("created_at").first()
        assertEquals(recentFolder, allFolders[0].folder.copy(
            createdAt = recentFolder.createdAt
        ))
        assertEquals(oldestFolder, allFolders[1].folder.copy(
            createdAt = oldestFolder.createdAt
        ))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun folderDao_insertDuplicatedFolders_throwsSQLiteConstraintException() = runTest {
        folderDao.insert(folder1)
        folderDao.insert(
            folder1.copy(
                id = 2
            )
        )
    }
}
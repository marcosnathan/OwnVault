package com.marcosnathan.ownvault.database

import com.marcosnathan.ownvault.database.model.EncryptedFileEntity
import com.marcosnathan.ownvault.database.model.FolderEntity
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.time.Clock

class EncryptedFileDaoTest : DatabaseTest() {

    private val folder = FolderEntity(
        id = 1L,
        name = "Folder 1",
        createdAt = Clock.System.now()
    )

    private val folder2 = FolderEntity(
        id = 2L,
        name = "Folder 2",
        createdAt = Clock.System.now()
    )

    private val encryptedFilesFolder1 = listOf(
        EncryptedFileEntity(
            id = 1L,
            name = "my_passwords.txt",
            size = 15L,
            savedPath = "/tmp/my_passwords.enc.txt",
            extension = "txt",
            folderId = folder.id
        ),
        EncryptedFileEntity(
            id = 2L,
            name = "my_phrases.txt",
            size = 20L,
            savedPath = "/tmp/my_phrases.enc.txt",
            extension = "txt",
            folderId = folder.id
        ),
        EncryptedFileEntity(
            id = 3L,
            name = "my_pdf.pdf",
            size = 20L,
            savedPath = "/tmp/my_pdf.enc.pdf",
            extension = "pdf",
            folderId = folder.id
        )
    )

    private val encryptedFilesFolder2 = listOf(
        EncryptedFileEntity(
            id = 1L,
            name = "my_passwords2.txt",
            size = 15L,
            savedPath = "/tmp/my_passwords2.enc.txt",
            extension = "txt",
            folderId = folder2.id
        ),
        EncryptedFileEntity(
            id = 2L,
            name = "my_phrases2.txt",
            size = 20L,
            savedPath = "/tmp/my_phrases2.enc.txt",
            extension = "txt",
            folderId = folder2.id
        ),
        EncryptedFileEntity(
            id = 3L,
            name = "my_pdf2.pdf",
            size = 20L,
            savedPath = "/tmp/my_pdf2.enc.pdf",
            extension = "pdf",
            folderId = folder2.id
        )
    )

    private suspend fun insertFolders() {
        folderDao.insert(folder)
        folderDao.insert(folder2)
    }

    private suspend fun insertFilesOnFolders() {
        encryptedFileDao.insertFiles(encryptedFilesFolder1)
        encryptedFileDao.insertFiles(encryptedFilesFolder2)
    }

    private suspend fun initAll() {
        insertFolders()
        insertFilesOnFolders()
    }

    @Test
    fun encryptedFileDao_insertFiles_filesSaved() = runTest {
        insertFolders()
        encryptedFileDao.insertFiles(encryptedFilesFolder1)
        val allEncryptedFiles = encryptedFileDao.getAll(
            folderId = folder.id
        ).first()
        assertEquals(encryptedFilesFolder1.size, allEncryptedFiles.size)
    }

    @Test
    fun encryptedFileDao_deleteFile_fileFromFolderDeleted() = runTest {
        initAll()
        val fileToBeDeleted = encryptedFilesFolder1[0]
        encryptedFileDao.deleteFiles(listOf(fileToBeDeleted.id))
        val allEncryptedFilesFromFolder = encryptedFileDao.getAll(
            folderId = folder.id
        ).first()
        assertEquals(encryptedFilesFolder1.size - 1, allEncryptedFilesFromFolder.size)
    }

    @Test
    fun encryptedFileDao_updateFile_fileUpdated() = runTest {
        initAll()
        val fileToBeUpdated = encryptedFilesFolder1[0].copy(
            name = "renamed_file"
        )
        encryptedFileDao.update(fileToBeUpdated)
        val fileUpdated = encryptedFileDao.getOne(fileToBeUpdated.id).filterNotNull().first()
        assertEquals(
            fileToBeUpdated,
            fileUpdated.copy(encryptedAt = fileToBeUpdated.encryptedAt)
        )
    }

    @Test
    fun encryptedFileDao_deleteFolder_areFilesFromFolderDeleted() = runTest {
        initAll()
        folderDao.delete(folder.id)
        val allItemsFromFolder = encryptedFileDao.getAll(
            folderId = folder.id
        ).first()
        assertEquals(emptyList<EncryptedFileEntity>(), allItemsFromFolder)
    }

    @Test
    fun encryptedFileDao_insertDuplicatedFile_fileIsIgnored() = runTest {
        initAll()
        val duplicatedFile = encryptedFilesFolder1[0]
        val insertedFiles = encryptedFileDao.insertFiles(listOf(duplicatedFile))
        assertTrue(insertedFiles.all { it == -1L })
    }
}
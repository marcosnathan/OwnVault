package com.marcosnathan.ownvault.data

import com.marcosnathan.ownvault.data.datasource.local.database.dao.EncryptedFileDao
import com.marcosnathan.ownvault.data.datasource.local.database.model.EncryptedFileEntity
import com.marcosnathan.ownvault.data.datasource.local.database.model.asExternalModel
import com.marcosnathan.ownvault.data.datasource.local.database.model.toEntity
import com.marcosnathan.ownvault.model.EncryptedFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import kotlin.collections.map

enum class FileOrder(val column: String){
    DATA("created_at"),
    NAME("name"),
    SIZE("size")
}

interface EncryptedFileRepository {
    fun getAll(
        folderId: Long,
        order: FileOrder = FileOrder.NAME
    ) : Flow<List<EncryptedFile>>

    fun getOne(id: Long) : Flow<EncryptedFile?>

    suspend fun deleteFiles(ids: List<Long>)

    suspend fun update(encryptedFile: EncryptedFile)

    suspend fun insertFiles(folderId: Long, encryptedFiles: List<EncryptedFile>) : List<Long>

    suspend fun insert(folderId: Long, encryptedFile: EncryptedFile)

}

@Single
class OfflineEncryptedFileRepository(
    private val encryptedFileDao: EncryptedFileDao
) : EncryptedFileRepository {
    override fun getAll(
        folderId: Long,
        order: FileOrder
    ): Flow<List<EncryptedFile>> = encryptedFileDao.getAll(
        folderId = folderId,
        column = order.column
    ).map {
        it.map(EncryptedFileEntity::asExternalModel)
    }

    override fun getOne(id: Long): Flow<EncryptedFile?> = encryptedFileDao.getOne(id).map { it?.asExternalModel() }

    override suspend fun deleteFiles(ids: List<Long>) = encryptedFileDao.deleteFiles(ids)

    override suspend fun update(encryptedFile: EncryptedFile) = encryptedFileDao.update(encryptedFile.toEntity())

    override suspend fun insertFiles(folderId: Long,encryptedFiles: List<EncryptedFile>): List<Long> =
        encryptedFileDao.insertFiles(
            encryptedFiles.map { it.toEntity(folderId) }
        )

    override suspend fun insert(folderId: Long, encryptedFile: EncryptedFile) =
        encryptedFileDao.insert(
            encryptedFile.toEntity(folderId)
        )

}


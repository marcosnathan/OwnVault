package com.marcosnathan.ownvault.data

import com.marcosnathan.ownvault.database.dao.FolderDao
import com.marcosnathan.ownvault.database.model.FolderEntity
import com.marcosnathan.ownvault.database.model.asExternalModel
import com.marcosnathan.ownvault.database.model.toEntity
import com.marcosnathan.ownvault.model.Folder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class FolderOrder(val column: String) {
    DATA("created_at"),
    NAME("name"),
    SIZE("size")
}

interface FolderRepository {
    fun getOne(id: Long): Flow<Folder?>

    suspend fun delete(id: Long)

    suspend fun insert(folder: Folder)

    suspend fun update(folder: Folder)

    fun getAll(folderOrder: FolderOrder = FolderOrder.NAME) : Flow<List<Folder>>
}

class OfflineFolderRepository(
    private val folderDao: FolderDao
) : FolderRepository {

    override fun getOne(id: Long): Flow<Folder?> = folderDao.getOne(id)
        .map { it?.asExternalModel() }

    override suspend fun delete(id: Long) = folderDao.delete(id)

    override suspend fun insert(folder: Folder) = folderDao.insert(folder.toEntity())

    override suspend fun update(folder: Folder) = folderDao.update(folder.toEntity())

    override fun getAll(folderOrder: FolderOrder): Flow<List<Folder>> =
        folderDao.getAll(folderOrder.column).map {
            it.map(FolderEntity::asExternalModel)
        }
}
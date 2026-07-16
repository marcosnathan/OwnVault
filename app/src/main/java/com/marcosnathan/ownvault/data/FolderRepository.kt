package com.marcosnathan.ownvault.data

import com.marcosnathan.ownvault.data.datasource.local.database.dao.FolderDao
import com.marcosnathan.ownvault.data.datasource.local.database.model.FolderEntity
import com.marcosnathan.ownvault.data.datasource.local.database.model.asExternalModel
import com.marcosnathan.ownvault.data.datasource.local.database.model.toEntity
import com.marcosnathan.ownvault.model.Folder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

enum class FolderOrder {
    DATE,
    NAME
}



interface FolderRepository {
    fun getOne(id: Long): Flow<Folder?>

    suspend fun delete(id: Long)

    suspend fun deleteFolders(ids: List<Long>)

    suspend fun insert(folder: Folder)

    suspend fun update(folder: Folder)

    fun getAll(folderOrder: FolderOrder) : Flow<List<Folder>>
}

@Single
class OfflineFolderRepository(
    private val folderDao: FolderDao
) : FolderRepository {

    override fun getOne(id: Long): Flow<Folder?> = folderDao.getOne(id)
        .map { it?.asExternalModel() }

    override suspend fun delete(id: Long) = folderDao.delete(id)

    override suspend fun deleteFolders(ids: List<Long>) = folderDao.deleteFolders(ids)

    override suspend fun insert(folder: Folder) = folderDao.insert(folder.toEntity())

    override suspend fun update(folder: Folder) = folderDao.update(folder.toEntity())

    override fun getAll(folderOrder: FolderOrder): Flow<List<Folder>> = when(folderOrder){
        FolderOrder.DATE -> folderDao.getAllByDateDesc()
        FolderOrder.NAME -> folderDao.getAllByNameDesc()
    }.map {
        it.map(FolderEntity::asExternalModel)
    }

}
package com.marcosnathan.ownvault.fake

import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.data.FolderRepository
import com.marcosnathan.ownvault.data.datasource.local.database.model.FolderEntity
import com.marcosnathan.ownvault.data.datasource.local.database.model.asExternalModel
import com.marcosnathan.ownvault.data.datasource.local.database.model.toEntity
import com.marcosnathan.ownvault.fake.datasource.FakeRoomDataSource
import com.marcosnathan.ownvault.model.Folder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeFolderRepository : FolderRepository {

    val folders = MutableStateFlow(
        FakeRoomDataSource.fakeFolders
    )

    override fun getOne(id: Long): Flow<Folder?> = folders.map { entities ->
        entities.first { it.id == id }.asExternalModel()
    }

    override suspend fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFolders(ids: List<Long>) {
        folders.update { folders ->
            folders.filterNot { it.id in ids }
        }
    }

    override suspend fun insert(folder: Folder) {
        if (folders.value.any { it.id == folder.id || it.name == folder.name }){
            throw IllegalStateException("Duplicated folder")
        }
        val sortedById = folders.value.sortedBy { it.id }
        val nextId = sortedById.last().id + 1
        val newFolder = folder.copy(id = nextId)
        folders.update { oldState ->
            (listOf(newFolder.toEntity()) + oldState).distinctBy(FolderEntity::id)
        }
    }

    override suspend fun update(folder: Folder) {
        TODO("Not yet implemented")
    }

    override fun getAll(folderOrder: FolderOrder): Flow<List<Folder>> = folders.map { entities ->
            entities.sortedWith(
                    when (folderOrder) {
                        FolderOrder.NAME -> compareBy { it.name.lowercase() }
                        FolderOrder.DATE -> compareBy { it.createdAt }
                    }
                ).map(FolderEntity::asExternalModel)
        }

}

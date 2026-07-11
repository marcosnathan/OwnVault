package com.marcosnathan.ownvault.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.marcosnathan.ownvault.database.model.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Transaction
    @Query("SELECT * FROM folders ORDER BY :column DESC")
    fun getAll(column: String = "name") : Flow<List<FolderEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(folder: FolderEntity)

    @Query("DELETE FROM folders WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM folders WHERE id in (:ids)")
    suspend fun deleteFolders(ids: List<Long>)

    @Update
    suspend fun update(folderEntity: FolderEntity)

    @Transaction
    @Query("SELECT * FROM folders WHERE id = :id")
    fun getOne(id: Long) : Flow<FolderEntity?>
}
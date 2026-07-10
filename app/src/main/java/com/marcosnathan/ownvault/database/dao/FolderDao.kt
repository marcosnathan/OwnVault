package com.marcosnathan.ownvault.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.marcosnathan.ownvault.database.model.FolderEntity
import com.marcosnathan.ownvault.database.model.FolderWithFiles
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Transaction
    @Query("SELECT * FROM folders ORDER BY :column DESC")
    fun getAll(column: String = "name") : Flow<List<FolderWithFiles>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(folder: FolderEntity)

    @Delete
    suspend fun delete(folderEntity: FolderEntity)

    @Update
    suspend fun update(folderEntity: FolderEntity)

    @Transaction
    @Query("SELECT * FROM folders WHERE id = :id")
    fun getOne(id: Long) : Flow<FolderWithFiles?>
}
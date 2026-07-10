package com.marcosnathan.ownvault.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.marcosnathan.ownvault.database.model.EncryptedFileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EncryptedFileDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(encryptedFileEntity: EncryptedFileEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFiles(encryptedFiles: List<EncryptedFileEntity>) : List<Long>

    @Query("""
        SELECT * FROM encrypted_files WHERE id = :id
    """)
    fun getOne(id: Long) : Flow<EncryptedFileEntity?>

    @Transaction
    @Query("""
        SELECT * FROM encrypted_files JOIN folders f WHERE f.id = :folderId ORDER BY :column DESC
    """)
    fun getAll(
        folderId: Long,
        column: String = "name"
    ) : Flow<List<EncryptedFileEntity>>

    @Query(
        """
            DELETE FROM encrypted_files WHERE id in (:ids)
        """
    )
    suspend fun deleteFiles(ids: List<Long>)

    @Update
    suspend fun update(encryptedFileEntity: EncryptedFileEntity)
}
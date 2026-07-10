package com.marcosnathan.ownvault.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcosnathan.ownvault.model.EncryptedFile
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(tableName = "encrypted_files")
data class EncryptedFileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val size: Long,
    @ColumnInfo("saved_path")
    val savedPath: String,
    val extension: String,
    val encryptedAt: Instant = Clock.System.now(),

    @ColumnInfo(name = "folder_id")
    val folderId: Long
)

fun EncryptedFileEntity.asExternalModel() = EncryptedFile(
    id = id,
    name = name,
    size = size,
    savedPath = savedPath,
    extension = extension,
    encryptedAt = encryptedAt
)

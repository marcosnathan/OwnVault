package com.marcosnathan.ownvault.data.datasource.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.marcosnathan.ownvault.model.EncryptedFile
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(
    tableName = "encrypted_files",
    foreignKeys = [
        ForeignKey(
            entity = FolderEntity::class,
            parentColumns = ["id"],
            childColumns = ["folder_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EncryptedFileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val size: Long,
    @ColumnInfo("saved_path")
    val savedPath: String,
    val extension: String,
    val encryptedAt: Instant = Clock.System.now(),

    @ColumnInfo(name = "folder_id", index = true)
    val folderId: Long? = null
)

fun EncryptedFileEntity.asExternalModel() = EncryptedFile(
    id = id,
    name = name,
    size = size,
    savedPath = savedPath,
    extension = extension,
    encryptedAt = encryptedAt,
)

fun EncryptedFile.toEntity(folderId: Long? = null) = EncryptedFileEntity(
    id = id,
    name = name,
    size = size,
    savedPath = savedPath,
    extension = extension,
    folderId = folderId
)

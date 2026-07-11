package com.marcosnathan.ownvault.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.marcosnathan.ownvault.model.Folder
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(tableName = "folders",
    indices = [Index(value = ["name"], unique = true)]
)
data class FolderEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val password: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now()
)

data class FolderWithFiles(
    @Embedded val folder: FolderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "folder_id"
    )
    val encryptedFiles: List<EncryptedFileEntity> = emptyList()
)


fun FolderWithFiles.asExternalModel() = Folder(
    id = folder.id,
    name = folder.name,
    files = encryptedFiles.map(EncryptedFileEntity::asExternalModel),
    isProtected = folder.password != null,
    createdAt = folder.createdAt,
)

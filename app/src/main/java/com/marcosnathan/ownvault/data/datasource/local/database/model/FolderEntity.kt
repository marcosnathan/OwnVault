package com.marcosnathan.ownvault.data.datasource.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marcosnathan.ownvault.model.Folder
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(tableName = "folders",
    indices = [Index(value = ["name"], unique = true)]
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val password: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now()
)


fun FolderEntity.asExternalModel() = Folder(
    id = id,
    name = name,
    isProtected = password != null,
    createdAt = createdAt
)

fun Folder.toEntity() = FolderEntity(
    id = id,
    name = name,
)

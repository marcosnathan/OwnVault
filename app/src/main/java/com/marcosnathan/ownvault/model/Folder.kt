package com.marcosnathan.ownvault.model

import kotlin.time.Instant

data class Folder(
    val id: Long,
    val name: String,
    val files: List<EncryptedFile>,
    val isProtected: Boolean,
    val createdAt: Instant,
)

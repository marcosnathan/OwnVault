package com.marcosnathan.ownvault.model

import kotlin.time.Instant


data class EncryptedFile(
    val id: Long,
    val name: String,
    val size: Long,
    val savedPath: String,
    val extension: String,
    val encryptedAt: Instant
)

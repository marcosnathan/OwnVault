package com.marcosnathan.ownvault.model

import kotlin.time.Clock
import kotlin.time.Instant

data class Folder(
    val id: Long = 0,
    val name: String,
    val isProtected: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
)

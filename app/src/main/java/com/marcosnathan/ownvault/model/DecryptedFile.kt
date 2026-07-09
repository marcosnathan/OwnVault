package com.marcosnathan.ownvault.model

import java.io.File

data class DecryptedFile(
    val name: String,
    val size: Long,
    val tmpPath: String,
    val extension: String,
) {
    fun delete() : Boolean = File(tmpPath).delete()
}

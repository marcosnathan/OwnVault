package com.marcosnathan.ownvault.crypto

import java.io.InputStream
import java.nio.file.Path

data class EncryptionParams(
    val input: InputStream,
    val pin: String
)

data class DecryptionParams(
    val bytes: ByteArray,
    val pin: String,
    val path: Path? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DecryptionParams

        if (!bytes.contentEquals(other.bytes)) return false
        if (pin != other.pin) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bytes.contentHashCode()
        result = 31 * result + pin.hashCode()
        result = 31 * result + (path?.hashCode() ?: 0)
        return result
    }

}

data class EncryptionResult(
    val bytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptionResult

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}

data class DecryptionResult(
    val file: Path
)



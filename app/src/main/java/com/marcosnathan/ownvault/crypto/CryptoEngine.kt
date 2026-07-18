package com.marcosnathan.ownvault.crypto

import org.koin.core.annotation.Single

interface CryptoEngine {
    suspend fun encrypt(params: EncryptionParams) : Result<EncryptionResult>

    suspend fun decrypt(params: DecryptionParams) : Result<DecryptionResult>
}

@Single
class DefaultCryptoEngine(

) : CryptoEngine {

    override suspend fun encrypt(params: EncryptionParams): Result<EncryptionResult> {
        TODO("Not yet implemented")
    }

    override suspend fun decrypt(params: DecryptionParams): Result<DecryptionResult> {
        TODO("Not yet implemented")
    }

}
package com.holzed.applicationscanner.data

import com.holzed.applicationscanner.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

interface HashProvider {
    /**
     * Calculates the SHA-256 hash of the file at the given path.
     *
     * @param filePath The absolute path to the APK file.
     * @return The MD5 hash of the file as a lowercase hexadecimal string.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws NoSuchAlgorithmException If SHA-256 algorithm is not available.
     */
    suspend fun calculateHash(filePath: String): String
}

private const val BUFFER_SIZE = 512 * 1024

class HashProviderImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : HashProvider {

    override suspend fun calculateHash(filePath: String): String = withContext(ioDispatcher) {
        val file = File(filePath)
        val buffer = ByteArray(BUFFER_SIZE)
        val digest = MessageDigest.getInstance("MD5")

        file.inputStream().use { input ->
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                digest.update(buffer, 0, bytesRead)
            }
        }

        digest.digest().joinToString("") { "%02x".format(it) }
    }
}

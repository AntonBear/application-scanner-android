package com.holzed.applicationscanner.data

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HashRepository @Inject constructor(private val hashProvider: HashProvider) {
    private val cache = ConcurrentHashMap<String, String>()

    suspend fun getHash(packageName: String, sourceDir: String): String {
        cache[packageName]?.let { return it }
        return hashProvider.calculateHash(sourceDir).also { cache[packageName] = it }
    }
}

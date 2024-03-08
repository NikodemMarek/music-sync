package com.example.musicsync.providers

import com.example.musicsync.data.Track

interface Provider {
    suspend fun getAllTracks(): List<Track>

    fun isAuthenticated(): Boolean
}

enum class ProviderType {
    POLARIS,
}

object ProviderFactory {
    fun getInstance(type: ProviderType) =
        when (type) {
            ProviderType.POLARIS -> this.getPolarisInstance()
        }

    private val default = ProviderType.POLARIS

    fun getDefaultInstance() = this.getInstance(default)

    @Volatile private var polarisInstance: Polaris? = null

    fun getPolarisInstance() =
        polarisInstance ?: synchronized(this) {
            polarisInstance ?: Polaris().also { polarisInstance = it }
        }
}

interface NoAuth

interface PasswordAuth {
    suspend fun auth(
        username: String,
        password: String,
    )
}

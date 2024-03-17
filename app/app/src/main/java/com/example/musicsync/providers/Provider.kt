package com.example.musicsync.providers

import com.example.musicsync.data.Track

interface Provider {
    suspend fun getAllTracks(): List<Track>

    suspend fun addTrack(track: Track)

    fun isAuthenticated(): Boolean
}

enum class ProviderType {
    POLARIS,
    CUSTOM_SERVER,
}

object ProviderFactory {
    fun getInstance(type: ProviderType) =
        when (type) {
            ProviderType.POLARIS -> this.getPolarisInstance()
            ProviderType.CUSTOM_SERVER -> this.getCustomServerInstance()
        }

    private val default = ProviderType.CUSTOM_SERVER

    fun getDefaultInstance() = this.getInstance(default)

    @Volatile private var polarisInstance: Polaris? = null

    fun getPolarisInstance() =
        polarisInstance ?: synchronized(this) {
            polarisInstance ?: Polaris().also { polarisInstance = it }
        }

    @Volatile private var customServerInstance: CustomServer? = null

    fun getCustomServerInstance() =
        customServerInstance ?: synchronized(this) {
            customServerInstance ?: CustomServer().also { customServerInstance = it }
        }
}

interface NoAuth

interface PasswordAuth {
    suspend fun auth(
        username: String,
        password: String,
    )

    suspend fun logout()
}

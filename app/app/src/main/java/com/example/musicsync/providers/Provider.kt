package com.example.musicsync.providers

import com.example.musicsync.data.Track

interface Provider {
    suspend fun getAllTracks(): List<Track>
}

interface NoAuth

interface PasswordAuth {
    suspend fun auth(
        username: String,
        password: String,
    )

    fun isAuthenticated(): Boolean
}

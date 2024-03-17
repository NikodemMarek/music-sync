package com.example.musicsync.providers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.musicsync.App
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_cache")

object AuthCacheKeys {
    val POLARIS_TOKEN = stringPreferencesKey("polaris_token")
    val CUSTOM_SERVER_TOKEN = stringPreferencesKey("custom_server_token")
}

class AuthCache() {
    private var dataStore: DataStore<Preferences>

    init {
        dataStore = App.getContext().dataStore
    }

    suspend fun getFor(key: Preferences.Key<String>): String? {
        val data = dataStore.data.first()[key]

        return if (data.isNullOrEmpty()) {
            null
        } else {
            data
        }
    }

    suspend fun setFor(
        key: Preferences.Key<String>,
        data: String?,
    ) {
        val data = data ?: ""
        dataStore.edit { preferences ->
            preferences[key] = data
        }
    }
}

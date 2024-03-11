package com.example.musicsync.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicsync.providers.ProviderFactory
import com.example.musicsync.providers.ProviderType
import kotlinx.coroutines.launch

class AuthUsernamePasswordViewModel : ViewModel() {
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var error = MutableLiveData<String>()

    lateinit var onFinish: () -> Unit
    lateinit var providerType: ProviderType

    init {
        username.value = ""
        password.value = ""
        error.value = ""
    }

    fun login() {
        error.value = ""
        val provider = ProviderFactory.getInstance(providerType)

        if (username.value.isNullOrEmpty()) {
            error.value = "Username is required"
            return
        }

        if (password.value.isNullOrEmpty()) {
            error.value = "Password is required"
            return
        }

        viewModelScope.launch {
            try {
                provider.auth(username.value ?: "", password.value ?: "")
                onFinish()
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}

package com.example.musicsync

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.musicsync.databinding.ActivityAuthBinding
import com.example.musicsync.providers.ProviderFactory
import com.example.musicsync.providers.ProviderType
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity(R.layout.activity_auth) {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val providerType = intent.getSerializableExtra("provider") as ProviderType?
        if (providerType == null) {
            finish()
            return
        }

        val provider = ProviderFactory.getInstance(providerType)

        binding.bLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    provider.auth(username, password)
                    finish()
                } catch (e: Exception) {
                    Log.e("xxx", "Error authenticating", e)
                }
            }
        }
    }
}

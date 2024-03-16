package com.example.musicsync

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.musicsync.databinding.ActivityAuthBinding
import com.example.musicsync.fragments.AuthUsernamePasswordFragment
import com.example.musicsync.providers.PasswordAuth
import com.example.musicsync.providers.Provider
import com.example.musicsync.providers.ProviderFactory
import com.example.musicsync.providers.ProviderType

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

        val provider = ProviderFactory.getInstance(providerType) as Provider

        if (provider.isAuthenticated()) {
            finish()
            return
        }

        when (provider) {
            is PasswordAuth -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<AuthUsernamePasswordFragment>(
                        binding.authFragmentContainer.id,
                        args =
                            Bundle().apply {
                                putSerializable("provider", providerType)
                            },
                    )
                }
            }
        }
    }
}

package com.example.musicsync.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.musicsync.App
import com.example.musicsync.AuthActivity
import com.example.musicsync.databinding.FragmentHomeBinding
import com.example.musicsync.providers.PasswordAuth
import com.example.musicsync.providers.Provider
import com.example.musicsync.providers.ProviderFactory
import com.example.musicsync.providers.ProviderType
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val provider = ProviderFactory.getDefaultInstance() as Provider

        binding.bLogout.setOnClickListener {
            lifecycleScope.launch {
                try {
                    when (provider) {
                        is PasswordAuth -> provider.logout()
                    }

                    val intent = Intent(App.getContext(), AuthActivity::class.java)
                    intent.putExtra("provider", ProviderType.CUSTOM_SERVER)

                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return binding.root
    }
}

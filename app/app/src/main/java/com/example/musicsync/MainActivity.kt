package com.example.musicsync

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.musicsync.databinding.ActivityMainBinding
import com.example.musicsync.fragments.FetchFragment
import com.example.musicsync.fragments.HomeFragment
import com.example.musicsync.providers.PasswordAuth
import com.example.musicsync.providers.Polaris
import com.example.musicsync.providers.Provider
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var provider: Provider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container_view)
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_home -> {
                    supportFragmentManager.commit {
                        replace<HomeFragment>(R.id.fragment_container_view)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.menu_item_fetch -> {
                    supportFragmentManager.commit {
                        replace<FetchFragment>(R.id.fragment_container_view)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                else -> false
            }
        }

        provider = Polaris.getInstance()

        lifecycleScope.launch {
            try {
                when (provider) {
                    is PasswordAuth -> {
                        (provider as Polaris).auth("test", "test")
                    }
                }

                val tracks = provider.getAllTracks()
                Log.d("xxx", "Fetched tracks: $tracks")
            } catch (e: Exception) {
                // handle exception
                Log.e("xxx", "Error fetching tracks", e)
            }
        }
    }
}

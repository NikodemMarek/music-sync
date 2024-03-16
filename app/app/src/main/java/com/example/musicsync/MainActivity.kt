package com.example.musicsync

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.musicsync.databinding.ActivityMainBinding
import com.example.musicsync.fragments.FetchFragment
import com.example.musicsync.fragments.HomeFragment
import com.example.musicsync.providers.ProviderFactory
import com.example.musicsync.providers.ProviderType

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding

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

        val provider = ProviderFactory.getCustomServerInstance()
        if (!provider.isAuthenticated()) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("provider", ProviderType.CUSTOM_SERVER)

            startActivity(intent)
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
    }
}

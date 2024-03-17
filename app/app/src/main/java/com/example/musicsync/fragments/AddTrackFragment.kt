package com.example.musicsync.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicsync.databinding.FragmentAddTrackBinding
import com.example.musicsync.providers.Provider
import com.example.musicsync.providers.ProviderFactory

class AddTrackFragment : Fragment() {
    private lateinit var binding: FragmentAddTrackBinding
    private lateinit var model: AddTrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddTrackBinding.inflate(inflater)
        model = ViewModelProvider(this).get(AddTrackViewModel::class.java)
        binding.lifecycleOwner = this
        binding.model = model

        model.provider = ProviderFactory.getDefaultInstance() as Provider

        return binding.root
    }
}

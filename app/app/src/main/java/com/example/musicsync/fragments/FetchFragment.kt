package com.example.musicsync.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicsync.components.FetchTrackListAdapter
import com.example.musicsync.databinding.FragmentFetchBinding

class FetchFragment : Fragment() {
    private lateinit var binding: FragmentFetchBinding
    private lateinit var model: FetchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFetchBinding.inflate(inflater)
        model = ViewModelProvider(this).get(FetchViewModel::class.java)
        binding.model = model

        binding.rvTracks.layoutManager = LinearLayoutManager(requireContext())
        model.getObservedItemList().observe(viewLifecycleOwner) {
            binding.rvTracks.adapter = FetchTrackListAdapter(it)
        }

        return binding.root
    }
}

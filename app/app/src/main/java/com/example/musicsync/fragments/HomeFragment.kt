package com.example.musicsync.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.musicsync.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var model: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        model = ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root
    }
    //
    // override fun onActivityCreated(savedInstanceState: Bundle?) {
    //     super.onActivityCreated(savedInstanceState)
    // }
}

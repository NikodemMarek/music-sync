package com.example.musicsync.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicsync.databinding.FragmentAuthUsernamePasswordBinding
import com.example.musicsync.providers.PasswordAuth
import com.example.musicsync.providers.ProviderFactory
import com.example.musicsync.providers.ProviderType

class AuthUsernamePasswordFragment : Fragment() {
    private lateinit var binding: FragmentAuthUsernamePasswordBinding
    private lateinit var model: AuthUsernamePasswordViewModel

    private val providerType: ProviderType? get() =
        (
            requireArguments().getSerializable(
                ARG_PROVIDER,
            ) as ProviderType
        )

    companion object {
        private const val ARG_PROVIDER = "provider"

        fun newInstance(provider: ProviderType) =
            AuthUsernamePasswordFragment().apply {
                arguments = bundleOf(ARG_PROVIDER to provider)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAuthUsernamePasswordBinding.inflate(inflater)
        model = ViewModelProvider(this).get(AuthUsernamePasswordViewModel::class.java)
        binding.lifecycleOwner = this
        binding.model = model

        model.provider = ProviderFactory.getInstance(providerType!!) as PasswordAuth
        model.onFinish = { activity?.finish() }

        return binding.root
    }
}

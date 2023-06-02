package com.example.apnahospital.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentAuthenticationBinding
import com.example.apnahospital.adapter.AuthAdapterPager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : Fragment() {

    private lateinit var _binding: FragmentAuthenticationBinding
    private  lateinit var authAdapterPager: AuthAdapterPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authAdapterPager = AuthAdapterPager(this)

        with(_binding) {
            authViewPager.adapter = authAdapterPager
            TabLayoutMediator(authTabLayout, authViewPager) { tab, position ->
                when(position) {
                    0 -> tab.text = resources.getText(R.string.signup)
                    1 -> tab.text = resources.getText(R.string.signin)
                }
            }.attach()
        }
    }
}
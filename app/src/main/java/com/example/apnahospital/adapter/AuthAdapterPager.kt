package com.example.apnahospital.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apnahospital.ui.auth.LoginFragment
import com.example.apnahospital.ui.auth.RegistrationFragment

class AuthAdapterPager(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = listOf(RegistrationFragment(), LoginFragment())
        return fragment[position]
    }
}
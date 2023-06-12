package com.example.apnahospital.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apnahospital.databinding.FragmentDocListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocListFragment : Fragment() {

    private lateinit var _binding: FragmentDocListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDocListBinding.inflate(inflater, container, false)
        return _binding.root
    }
}

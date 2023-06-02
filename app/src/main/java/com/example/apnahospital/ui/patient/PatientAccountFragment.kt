package com.example.apnahospital.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentPatientAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientAccountFragment : Fragment() {

    private lateinit var _binding: FragmentPatientAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientAccountBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view = R.id.navHostFragmentMain

        _binding.patientUpdateB.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_patientUpdateProfileFragment)
        }
    }
}
package com.example.apnahospital.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentPatientAppointmentBinding
import com.example.apnahospital.utils.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientAppointmentFragment : Fragment() {

    private lateinit var _binding: FragmentPatientAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientAppointmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.bookAppointmentB.setOnClickListener {
            navigateTo(requireView(), R.id.action_appointmentFragment_to_bookAppointmentFragment)
        }
    }
}

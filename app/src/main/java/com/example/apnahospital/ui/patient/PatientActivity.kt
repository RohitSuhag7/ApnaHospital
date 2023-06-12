package com.example.apnahospital.ui.patient

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.apnahospital.R
import com.example.apnahospital.databinding.ActivityPatientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {

        _binding.patientNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.navHostFragmentPatient).navigate(R.id.homeFragment)
                    true
                }

                R.id.navigation_appointment -> {
                    findNavController(R.id.navHostFragmentPatient).navigate(R.id.appointmentFragment)
                    true
                }

                R.id.navigation_account -> {
                    findNavController(R.id.navHostFragmentPatient).navigate(R.id.accountFragment)
                    true
                }

                else -> false
            }
        }
    }
}

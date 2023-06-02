package com.example.apnahospital.ui.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentPatientUpdateProfileBinding
import com.example.apnahospital.model.PatientInfo
import com.example.apnahospital.screenstate.FirebaseResponseState.FirebaseFailure
import com.example.apnahospital.screenstate.FirebaseResponseState.FirebaseLoading
import com.example.apnahospital.screenstate.FirebaseResponseState.FirebaseSuccess
import com.example.apnahospital.viewmodel.PatientViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientUpdateProfileFragment : Fragment() {

    private lateinit var _binding: FragmentPatientUpdateProfileBinding
    private val viewModel by viewModels<PatientViewModel>()
    private lateinit var patientInfo: PatientInfo

    private var id = ""
    private var imageUrl = ""
    private var name = ""
    private var email = ""
    private var phoneNumber = ""
    private  var genderButton = ""
    private var age = ""
    lateinit var navBar : BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientUpdateProfileBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRadioButtonValue(_binding.patientGenderUpdateRG)

        _binding.patientUpdateProfileB.setOnClickListener {
            updatePatientProfileInfo()
        }
//        navBar  = requireActivity().findViewById(R.id.patientNavView)
//        navBar.visibility = View.GONE
    }

    private fun getRadioButtonValue(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            genderButton = if (R.id.maleUpdateRB == checkedId) "Male" else "Female"
        }
    }

    private fun getAgeFromDatePicker(datePicker: DatePicker) {
        datePicker.maxDate = System.currentTimeMillis()
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        age = "$day - ${month + 1} - $year"
    }

    private fun updatePatientProfileInfo() {
        with(_binding) {
            id = viewModel.currentUser?.uid.toString()
            imageUrl = patientUpdateImg.toString()
            name = patientUpdateNameTV.text.toString()
            email = patientUpdateEmailTV.text.toString()
            phoneNumber = patientUpdatePhoneNumberTV.text.toString()

            getAgeFromDatePicker(patientUpdateAgePicker)
        }

        if (name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()) {
            if (phoneNumber.length == 10) {
                patientInfo = PatientInfo(id, imageUrl, name, email, phoneNumber, genderButton, age)
                viewModel.setPatientInfo(patientInfo)
                lifeCycleScopeLaunch()
            } else {
                Toast.makeText(requireContext(), "Enter Valid Phone Number", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "Fill all the Fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun lifeCycleScopeLaunch() {
        lifecycleScope.launchWhenStarted {
            viewModel.patientStateFlow.collect { state ->
                when (state) {
                    FirebaseLoading -> {
                        Toast.makeText(requireContext(), "Updating....", Toast.LENGTH_LONG).show()
                    }
                    is FirebaseFailure -> {
                        Toast.makeText(requireContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show()
                    }
                    is FirebaseSuccess -> {
                        findNavController().navigate(R.id.action_patientUpdateProfileFragment_to_patientActivity)
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
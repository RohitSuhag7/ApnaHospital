package com.example.apnahospital.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentPatientAccountBinding
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.Constants
import com.example.apnahospital.utils.navigateTo
import com.example.apnahospital.viewmodel.PatientViewModel
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientAccountFragment : Fragment() {

    private lateinit var _binding: FragmentPatientAccountBinding
    private val viewModel by viewModels<PatientViewModel>()

    private var name = ""
    var imgUrl = ""
    var email = ""
    private var phoneNumber = ""
    var gender = ""
    var age = ""

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

        _binding.patientUpdateB.setOnClickListener {
            navigateTo(requireView(), R.id.action_accountFragment_to_patientUpdateProfileFragment)
        }
        lifeCycleScopeLaunch()
    }

    private fun getPatientProfileInfo() {
        with(_binding) {
            patientNameTV.text = name
            patientIV.tag = imgUrl
            patientAccountEmailTV.text = email
            patientAccountPhoneTV.text = phoneNumber
            patientAccountGenderTV.text = gender
            patientAccountAgeTV.text = age
        }
        viewModel.getPatientInfo()
    }

    private fun lifeCycleScopeLaunch() {
        lifecycleScope.launchWhenStarted {
            viewModel.patientStateFlow.collect { state ->
                when (state) {
                    FirebaseResponseState.FirebaseLoading -> {
                        Toast.makeText(
                            requireContext(),
                            "Please wait...",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is FirebaseResponseState.FirebaseFailure -> {
                        Toast.makeText(
                            requireContext(),
                            "Please check your Internet connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is FirebaseResponseState.FirebaseSuccess -> {
                        val result = (state.data) as? DataSnapshot
                        name = result?.child(Constants.NAME)?.value.toString()
//                        imgUrl = result.child("imgUrl").value.toString()
                        email = result?.child(Constants.EMAIL)?.value.toString()
                        phoneNumber = result?.child(Constants.PHONENUMBER)?.value.toString()
                        gender = result?.child(Constants.GENDER)?.value.toString()
                        age = result?.child(Constants.AGE)?.value.toString()
                        getPatientProfileInfo()
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "Please check your Internet connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}

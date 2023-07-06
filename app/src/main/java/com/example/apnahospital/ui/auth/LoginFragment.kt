package com.example.apnahospital.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentLoginBinding
import com.example.apnahospital.model.PatientInfo
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.navigateTo
import com.example.apnahospital.viewmodel.AuthViewModel
import com.example.apnahospital.viewmodel.PatientViewModel
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private var isDoc: Boolean = false
    private val authViewModel by viewModels<AuthViewModel>()
    private val patientViewModel by viewModels<PatientViewModel>()
    private var patientData: PatientInfo? = null

    private var loginEmail = ""
    private var loginPass = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        _binding.loginemailET.setText("rs@gmail.com")
//        _binding.loginpassE.setText("rs@123")

        _binding.loginB.setOnClickListener {
            patientLifecycleScope()
            userSignIn()
        }
    }

    private fun userSignIn() {
        with(_binding) {
            loginEmail = loginemailET.text.toString()
            loginPass = loginpassE.text.toString()
        }
        patientViewModel.getPatientInfo()
        authViewModel.signInUser(loginEmail, loginPass)
        lifeCycleScopeLaunch()
    }

    private fun lifeCycleScopeLaunch() {
        lifecycleScope.launchWhenStarted {
            delay(2000)
            authViewModel.signInStateFlow.collect { state ->
                when (state) {
                    FirebaseResponseState.FirebaseLoading -> {
                        Toast.makeText(
                            requireContext(),
                            "Please SignIn for proceed",
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
                        if (isDoc) {
                            TODO()
                        } else {
                            if (patientData?.firstLogin == true || patientData?.firstLogin == null) {
                                navigateTo(
                                    requireView(),
                                    R.id.action_authenticationFragment_to_patientUpdateProfileFragment
                                )
                            } else {
                                navigateTo(
                                    requireView(),
                                    R.id.action_authenticationFragment_to_patientActivity
                                )
                            }
                        }
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

    private fun patientLifecycleScope() {
        lifecycleScope.launchWhenStarted {
            patientViewModel.patientStateFlow.collect { state ->
                when (state) {
                    is FirebaseResponseState.FirebaseSuccess -> {
                        val result = (state.data) as? DataSnapshot
                        patientData = result?.getValue(PatientInfo::class.java)
                    }

                    else -> {}
                }
            }
        }
    }
}

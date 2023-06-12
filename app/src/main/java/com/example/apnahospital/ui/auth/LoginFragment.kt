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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private var isDoc: Boolean = false
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var patientInfo: PatientInfo

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

        _binding.loginB.setOnClickListener {
            userSignIn()
        }
    }

    private fun userSignIn() {
        with(_binding) {
            loginEmail = loginemailET.text.toString()
            loginPass = loginpassE.text.toString()
        }
        patientInfo = PatientInfo(null, null, null, null, null, null, null)
        authViewModel.signInUser(loginEmail, loginPass)

        lifeCycleScopeLaunch()
    }

    private fun lifeCycleScopeLaunch() {
        lifecycleScope.launchWhenStarted {
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
//                            if (isLogin==true) {
//                                Toast.makeText(requireContext(), "DoctorLogin", Toast.LENGTH_LONG).show()
////                                viewModel.isUserFirstTimeLogin(false)
//                                // TODO
//                            } else {
//                                // TODO
//                            }
                        } else {
                            if (patientInfo.isFirstLogin == true) {
                                navigateTo(requireView(), R.id.action_authenticationFragment_to_patientUpdateProfileFragment2)
                            } else {
                                navigateTo(requireView(), R.id.action_authenticationFragment_to_patientActivity)
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
}

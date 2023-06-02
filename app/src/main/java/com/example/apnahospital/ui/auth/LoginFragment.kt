package com.example.apnahospital.ui.auth

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentLoginBinding
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private var isDoc: Boolean = false
    private val viewModel by viewModels<AuthViewModel>()

    private lateinit var mPreferences: SharedPreferences

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

//        val firstTime = mPreferences.getBoolean("firstTime", true)
//        if (firstTime) {
//            val editor: SharedPreferences.Editor = mPreferences.edit()
//            editor.putBoolean("firstTime", false)
//            editor.commit()
//            showMiddleActivity()
//        }
    }

    private fun userSignIn() {
        with(_binding) {
            loginEmail = loginemailET.text.toString()
            loginPass = loginpassE.text.toString()
        }
        viewModel.signInUser(loginEmail, loginPass)

        lifeCycleScopeLaunch()
    }

    private fun lifeCycleScopeLaunch() {
        lifecycleScope.launchWhenStarted {

            viewModel.getIsUserFirstTimeLogin.isCompleted

            viewModel.signInStateFlow.collect { state ->
                when(state) {
                    FirebaseResponseState.FirebaseLoading -> {
                        Toast.makeText(requireContext(), "Please SignIn for proceed", Toast.LENGTH_LONG).show()
                    }
                    is FirebaseResponseState.FirebaseFailure -> {
                        Toast.makeText(requireContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show()
                    }
                    is FirebaseResponseState.FirebaseSuccess -> {
                        if (isDoc) {
                            // TODO
                        } else {
                            findNavController().navigate(R.id.action_authenticationFragment_to_patientUpdateProfileFragment2)
                        }
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
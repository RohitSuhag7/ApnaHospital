package com.example.apnahospital.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.apnahospital.databinding.FragmentRegistrationBinding
import com.example.apnahospital.screenstate.FirebaseResponseState.FirebaseSuccess
import com.example.apnahospital.screenstate.FirebaseResponseState.FirebaseFailure
import com.example.apnahospital.screenstate.FirebaseResponseState.FirebaseLoading
import com.example.apnahospital.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var _binding: FragmentRegistrationBinding
    private var isDoc: Boolean = false
    private val viewModel by viewModels<AuthViewModel>()

    private var fullName = ""
    private var registerEmail = ""
    private var registerPass = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            isDoc = it.getBoolean("isDoc")
        }

        _binding.signupB.setOnClickListener {
            userSignUp()
        }
    }

    private fun userSignUp() {
        with(_binding) {
            fullName = fullNameET.text.toString()
            registerEmail = registeremailET.text.toString()
            registerPass = registerpassE.text.toString()
        }
        viewModel.signUpUser(fullName, registerEmail, registerPass)

        lifeCycleScopeLaunch()
    }

    private fun lifeCycleScopeLaunch() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signUpStateFlow.collect { state ->
                    when(state) {
                        FirebaseLoading -> {
                            Toast.makeText(requireContext(), "You are successfully Signup, Please SignIn for proceed", Toast.LENGTH_LONG).show()
                        }
                        is FirebaseFailure -> {
                            Toast.makeText(requireContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show()
                        }
                        is FirebaseSuccess -> {
//                            findNavController().navigate(R.id.action_registrationFragment_to_authenticationFragment)
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
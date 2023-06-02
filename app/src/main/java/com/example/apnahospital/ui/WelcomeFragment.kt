package com.example.apnahospital.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var _binding: FragmentWelcomeBinding
    private var isDoc: Boolean = false
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(300)
            _binding.hospitalLottieAnim.visibility = View.GONE
            _binding.selectIdentityCV.visibility = View.VISIBLE
        }

        with(_binding) {
            selectPatientIV.setOnClickListener{
                isDoc = false
                bundle = Bundle()
                bundle.putBoolean("isDoc", isDoc)
                findNavController().navigate(R.id.action_welcomeFragment_to_authenticationFragment, bundle)
            }

            selectDoctorIV.setOnClickListener{
                isDoc = true
                bundle = Bundle()
                bundle.putBoolean("isDoc", isDoc)
                findNavController().navigate(R.id.action_welcomeFragment_to_authenticationFragment, bundle)
            }
        }
    }
}
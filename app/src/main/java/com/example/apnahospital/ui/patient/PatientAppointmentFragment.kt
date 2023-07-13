package com.example.apnahospital.ui.patient

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apnahospital.R
import com.example.apnahospital.adapter.AppointmentsAdapter
import com.example.apnahospital.databinding.FragmentPatientAppointmentBinding
import com.example.apnahospital.model.Appointments
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.navigateTo
import com.example.apnahospital.viewmodel.AppointmentsViewModel
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientAppointmentFragment : Fragment(), AppointmentsAdapter.DeleteCallBack {

    private lateinit var _binding: FragmentPatientAppointmentBinding
    private val appointmentsViewModel by viewModels<AppointmentsViewModel>()
    private var appointmentsList: ArrayList<Appointments?>? = arrayListOf()
    private lateinit var appointmentsAdapter: AppointmentsAdapter

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

        _binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        appointmentsViewModel.getAppointments()
        lifecycleFetchAppointments()
    }

    private fun lifecycleFetchAppointments() {
        lifecycleScope.launchWhenStarted {
            appointmentsViewModel.appointmentStateFlow.collect { state ->
                when (state) {
                    FirebaseResponseState.FirebaseLoading -> {
                        _binding.appointmentsProgressBar.visibility = View.VISIBLE
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
                        appointmentsList?.clear()
                        val result = (state.data) as? DataSnapshot
                        result?.children?.forEach {
                            val appointments = it.getValue(Appointments::class.java)
                            appointments.apply {
                                this?.key = it.key
                            }
                            appointmentsList?.add(appointments)
                        }

                        if (appointmentsList?.isNotEmpty() == true) {
                            _binding.appointmentsProgressBar.visibility = View.GONE
                            _binding.appointmentNoData.visibility = View.GONE

                            appointmentsAdapter = AppointmentsAdapter(
                                appointmentsList,
                                this@PatientAppointmentFragment
                            )
                            _binding.appointmentRecyclerView.adapter = appointmentsAdapter
                        } else {
                            _binding.appointmentsProgressBar.visibility = View.GONE
                            _binding.appointmentNoData.visibility = View.VISIBLE
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

    @SuppressLint("NotifyDataSetChanged")
    override fun deleteAppointmentItem(item: Appointments?) {
        lifecycleScope.launchWhenStarted {
            appointmentsViewModel.appointmentStateFlow.collect { state ->
                when (state) {
                    is FirebaseResponseState.FirebaseSuccess -> {
                        appointmentsViewModel.deleteAppointments(item)
                        appointmentsAdapter.notifyDataSetChanged()
                        appointmentsViewModel.getAppointments()
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

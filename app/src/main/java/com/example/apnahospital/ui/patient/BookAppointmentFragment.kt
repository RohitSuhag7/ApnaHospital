package com.example.apnahospital.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentBookAppointmentBinding
import com.example.apnahospital.model.Appointments
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.navigateTo
import com.example.apnahospital.viewmodel.AppointmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAppointmentFragment : Fragment() {

    private lateinit var _binding: FragmentBookAppointmentBinding
    private val appointmentsViewModel by viewModels<AppointmentsViewModel>()
    private lateinit var appointments: Appointments

    private var id = ""
    private var imageUrl = ""
    private var name = ""
    private var phoneNumber = ""
    private var relation = ""
    private var specialities = ""
    private var doctor = ""
    private var gender = ""
    private var age = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookAppointmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRadioButtonValue(_binding.bookAppointmentRadioGroup)

        _binding.bookAppointmentSaveB.setOnClickListener {
            setAppointmentsData()
        }
        _binding.bookAppointmentCancelB.setOnClickListener {
            navigateTo(
                requireView(),
                R.id.action_bookAppointmentFragment_to_appointmentFragment
            )
        }

        val relationItems = listOf("Myself", "Father", "Mother", "Son", "Daughter", "Other")
        val specialities = listOf(
            "Allergists/Immunologists",
            "Cardiologists",
            "Dermatologists",
            "Endocrinologists",
            "Emergency Medicine Specialists",
            "Family Physicians",
            "Gastroenterologists",
            "Hematologists"
        )
        val doctor = listOf("Rohit", "Sunil")

        val relationAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, relationItems)
        val diseaseAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, specialities)
        val doctorAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, doctor)

        _binding.bookAppointmentRelationTV.setAdapter(relationAdapter)
        _binding.bookAppointmentSpecialitiesTV.setAdapter(diseaseAdapter)
        _binding.bookAppointmentSelectDoctorTV.setAdapter(doctorAdapter)

        _binding.bookAppointmentSpecialitiesTV.setOnItemClickListener { _, _, position, _ ->
            val element = diseaseAdapter.getItem(position)

            if (element.equals("Cardiologists")) {
                _binding.bookAppointmentSelectDoctorETV.visibility = View.VISIBLE
            } else {
                _binding.bookAppointmentSelectDoctorETV.visibility = View.GONE
            }
        }
    }

    private fun getIdFromBinding() {
        with(_binding) {
            id = appointmentsViewModel.currentUser?.uid.toString()
            imageUrl = bookAppointmentPatientIV.toString()
            name = bookAppointmentPatientNameEV.text.toString()
            phoneNumber = bookAppointmentPatientPhoneEV.text.toString()
            relation = bookAppointmentRelationTV.text.toString()
            specialities = bookAppointmentSpecialitiesTV.text.toString()
            doctor = bookAppointmentSelectDoctorTV.text.toString()

            getAgeFromDatePicker(bookAppointmentDatePicker)
        }
    }

    private fun getRadioButtonValue(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            gender = if (R.id.maleUpdateRB == checkedId) "Male" else "Female"
        }
    }

    private fun getAgeFromDatePicker(datePicker: DatePicker) {
        datePicker.maxDate = System.currentTimeMillis()
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        age = "$day - ${month + 1} - $year"
    }

    private fun setAppointmentsData() {
        getIdFromBinding()
        if (name.isNotEmpty() && phoneNumber.isNotEmpty() && relation.isNotEmpty() && specialities.isNotEmpty() && doctor.isNotEmpty() && gender.isNotEmpty() && age.isNotEmpty()) {
            if (phoneNumber.length == 10) {
                appointments = Appointments(
                    id,
                    imageUrl,
                    name,
                    phoneNumber,
                    relation,
                    specialities,
                    doctor,
                    gender,
                    age
                )
                appointmentsViewModel.setAppointments(appointments)
                navigateTo(
                    requireView(),
                    R.id.action_bookAppointmentFragment_to_appointmentFragment
                )
            } else {
                Toast.makeText(requireContext(), "Enter Valid Phone Number", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(requireContext(), "Fill all the Fields", Toast.LENGTH_LONG).show()
        }
    }
}

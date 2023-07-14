package com.example.apnahospital.ui.patient

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentBookAppointmentBinding
import com.example.apnahospital.model.Appointments
import com.example.apnahospital.utils.Constants
import com.example.apnahospital.utils.navigateTo
import com.example.apnahospital.viewmodel.AppointmentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class BookAppointmentFragment : Fragment() {

    private lateinit var _binding: FragmentBookAppointmentBinding
    private val appointmentsViewModel by viewModels<AppointmentsViewModel>()
    private lateinit var appointments: Appointments

    private var key = ""
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAppointmentsData()

        getRadioButtonValue(_binding.bookAppointmentRadioGroup)
        getAgeFromDatePicker()

        _binding.bookAppointmentSaveB.setOnClickListener {
            setAppointmentsData()
        }
        _binding.bookAppointmentCancelB.setOnClickListener {
            navigateTo(
                requireView(),
                R.id.action_bookAppointmentFragment_to_appointmentFragment
            )
        }

        val relationItems = listOf("Myself", "Father", "Mother", "Son", "Daughter", "Wife", "Other")
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
            imageUrl = bookAppointmentPatientIV.toString()
            name = bookAppointmentPatientNameEV.text.toString()
            phoneNumber = bookAppointmentPatientPhoneEV.text.toString()
            relation = bookAppointmentRelationTV.text.toString()
            specialities = bookAppointmentSpecialitiesTV.text.toString()
            doctor = bookAppointmentSelectDoctorTV.text.toString()
        }
    }

    private fun getRadioButtonValue(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            gender = if (R.id.bookAppointmentMale == checkedId) "Male" else "Female"
        }
    }

    private fun getAgeFromDatePicker() {
        _binding.bookAppointmentSelectAgeIV.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(requireContext(), { _, yearOfYear, monthOfYear, dayOfMonth ->
                    age = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + yearOfYear)
                    _binding.bookAppointmentAgeTV.setText(age)
                }, year, month, day)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private fun setAppointmentsData() {
        getIdFromBinding()
        getAgeFromDatePicker()
        if (name.isNotEmpty() && phoneNumber.isNotEmpty() && relation.isNotEmpty() && specialities.isNotEmpty() && doctor.isNotEmpty() && gender.isNotEmpty() && age.isNotEmpty()) {
            if (phoneNumber.length == 10) {
                appointments = Appointments(
                    key,
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getAppointmentsData() {
        arguments.let {
            with(_binding) {
                val item = it?.getParcelable(Constants.APPOINTMENTS_ITEMS, Appointments::class.java)

                bookAppointmentPatientNameEV.setText(item?.name)
                bookAppointmentPatientPhoneEV.setText(item?.pnumber)
                bookAppointmentRelationTV.setText(item?.relation)
                bookAppointmentSpecialitiesTV.setText(item?.specialities)
                bookAppointmentSelectDoctorTV.setText(item?.doctor)
                bookAppointmentAgeTV.setText(item?.age)

                if (item?.gender == "Male") {
                    bookAppointmentMale.isChecked = true
                } else if (item?.gender == "Female") {
                    bookAppointmentFemale.isChecked = true
                }
            }
        }
    }
}

package com.example.apnahospital.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apnahospital.R
import com.example.apnahospital.databinding.AppointmentListItemBinding
import com.example.apnahospital.model.Appointments
import com.example.apnahospital.utils.Constants
import com.example.apnahospital.utils.navigateTo

class AppointmentsAdapter(
    private val appointmentsList: ArrayList<Appointments?>?,
    private val deleteCallBack: DeleteCallBack
) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    private lateinit var _binding: AppointmentListItemBinding
    private lateinit var bundle: Bundle

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding =
            AppointmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(_binding.root)
    }

    override fun getItemCount(): Int = appointmentsList?.size!!

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = appointmentsList?.get(position)
        _binding.listPatientName.text = "Patient: ${item?.name}"
        _binding.listPatientGender.text = "Gender: ${item?.gender}"
        _binding.listPatientDoctor.text = "Doctor: ${item?.doctor}"

        Glide.with(holder.itemView.context).load(item?.image).placeholder(R.drawable.patient)
            .into(_binding.listPatientImage)

        _binding.listPatientDelete.setOnClickListener {
            deleteCallBack.deleteAppointmentItem(item)
            Toast.makeText(
                holder.itemView.context,
                "Appointment Deleted",
                Toast.LENGTH_LONG
            ).show()
        }

        bundle = Bundle()

        bundle.putString(Constants.PATIENTS_IMAGE, item?.image)
        bundle.putString(Constants.PATIENT_NAME, item?.name)
        bundle.putString(Constants.PATIENT_PHONE_NUMBER, item?.pnumber)
        bundle.putString(Constants.PATIENT_RELATION, item?.relation)
        bundle.putString(Constants.PATIENT_SPECIALITIES, item?.specialities)
        bundle.putString(Constants.PATIENT_DOCTOR, item?.doctor)
        bundle.putString(Constants.PATIENT_GENDER, item?.gender)
        bundle.putString(Constants.PATIENT_AGE, item?.age)

        _binding.appointmentCV.setOnClickListener {
            navigateTo(holder.itemView, R.id.action_appointmentFragment_to_bookAppointmentFragment, bundle)
        }
    }

    interface DeleteCallBack {
        fun deleteAppointmentItem(item: Appointments?)
    }
}

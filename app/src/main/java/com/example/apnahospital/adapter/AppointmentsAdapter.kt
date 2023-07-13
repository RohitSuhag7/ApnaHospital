package com.example.apnahospital.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apnahospital.R
import com.example.apnahospital.databinding.AppointmentListItemBinding
import com.example.apnahospital.model.Appointments

class AppointmentsAdapter(
    private val appointmentsList: ArrayList<Appointments?>?,
    private val deleteCallBack: DeleteCallBack
) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    private lateinit var _binding: AppointmentListItemBinding

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
    }

    interface DeleteCallBack {
        fun deleteAppointmentItem(item: Appointments?)
    }
}

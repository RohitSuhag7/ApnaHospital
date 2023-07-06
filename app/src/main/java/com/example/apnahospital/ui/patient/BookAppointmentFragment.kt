package com.example.apnahospital.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.apnahospital.R
import com.example.apnahospital.databinding.FragmentBookAppointmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAppointmentFragment : Fragment() {

    private lateinit var _binding: FragmentBookAppointmentBinding

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

        val items = listOf("Yourself", "Father", "Mother", "Son", "Daughter", "Other")

        val adapter = ArrayAdapter(requireContext(), R.layout.relation_list_item, items)
        _binding.bookAppointmentRelationTV.setAdapter(adapter)
    }
}

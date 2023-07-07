package com.example.apnahospital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnahospital.model.Appointments
import com.example.apnahospital.repository.AppointmentsRepository
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(private val appointmentsRepository: AppointmentsRepository) :
    ViewModel() {

    private val _appointmentStateFlow: MutableStateFlow<FirebaseResponseState?> =
        MutableStateFlow(FirebaseResponseState.FirebaseLoading)
    val appointmentStateFlow: StateFlow<FirebaseResponseState?> = _appointmentStateFlow

    val currentUser: FirebaseUser?
        get() = appointmentsRepository.currentUser

    init {
        if (appointmentsRepository.currentUser != null) {
            _appointmentStateFlow.value = FirebaseResponseState.FirebaseSuccess(currentUser)
        }
    }

    fun setAppointments(appointments: Appointments) = viewModelScope.launch {
        _appointmentStateFlow.value = appointmentsRepository.setAppointment(appointments)
    }
}

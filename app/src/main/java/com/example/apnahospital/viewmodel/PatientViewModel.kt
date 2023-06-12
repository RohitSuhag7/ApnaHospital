package com.example.apnahospital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnahospital.model.PatientInfo
import com.example.apnahospital.repository.PatientRepository
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(private val patientRepository: PatientRepository) :
    ViewModel() {

    private val _patientStateFlow: MutableStateFlow<FirebaseResponseState?> = MutableStateFlow(FirebaseResponseState.FirebaseLoading)
    val patientStateFlow: StateFlow<FirebaseResponseState?> = _patientStateFlow

    val currentUser: FirebaseUser?
        get() = patientRepository.currentUser

    init {
        if (patientRepository.currentUser != null) {
            _patientStateFlow.value = FirebaseResponseState.FirebaseSuccess(currentUser)
        }
    }

    fun setPatientInfo(patientInfo: PatientInfo) = viewModelScope.launch {
        _patientStateFlow.value = patientRepository.updatePatientInfo(patientInfo)
    }

    fun getPatientInfo() = viewModelScope.launch {
        _patientStateFlow.value = patientRepository.getPatientInfo()
    }
}

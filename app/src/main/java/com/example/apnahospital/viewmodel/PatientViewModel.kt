package com.example.apnahospital.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnahospital.model.PatientInfo
import com.example.apnahospital.repository.PatientRepository
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.readString
import com.example.apnahospital.utils.writeUserDetails
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val context: Application
) :
    ViewModel() {

    private val _patientStateFlow: MutableStateFlow<FirebaseResponseState?> =
        MutableStateFlow(FirebaseResponseState.FirebaseLoading)
    val patientStateFlow: StateFlow<FirebaseResponseState?> = _patientStateFlow

    private val _patientLocalInfoStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    val patientLocalInfoStateFlow: StateFlow<String> = _patientLocalInfoStateFlow

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

    fun updateFirstLogin(firstLogin: Boolean) = viewModelScope.launch {
        _patientStateFlow.value = patientRepository.updateFirstLogin(firstLogin)
    }

    fun savePatientInfoLocal(patientInfo: PatientInfo?) = viewModelScope.launch(Dispatchers.IO) {
        patientInfo?.let { patientInfo ->
            context.writeUserDetails("patientInfo", patientInfo)
        }
    }

    fun readPatientInfoLocal() = viewModelScope.launch {
        context.readString("patientInfo").collect {
            _patientLocalInfoStateFlow.value = it
        }
    }
}

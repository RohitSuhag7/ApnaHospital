package com.example.apnahospital.repository

import com.example.apnahospital.model.PatientInfo
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

interface PatientRepository {

    val currentUser: FirebaseUser?

    val dbReference: DatabaseReference?

    suspend fun updatePatientInfo(patientInfo: PatientInfo?): FirebaseResponseState

    suspend fun getPatientInfo(): FirebaseResponseState

    suspend fun deletePatientInfo(patientInfo: PatientInfo?): FirebaseResponseState

    suspend fun updateFirstLogin(firstLogin: Boolean): FirebaseResponseState
}

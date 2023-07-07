package com.example.apnahospital.repository

import com.example.apnahospital.model.Appointments
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

interface AppointmentsRepository {

    val currentUser: FirebaseUser?

    val dbReference: DatabaseReference?

    suspend fun setAppointment(appointments: Appointments?): FirebaseResponseState

    suspend fun getAppointments(): FirebaseResponseState

    suspend fun deleteAppointments(): FirebaseResponseState

    suspend fun updateAppointments(appointments: Appointments): FirebaseResponseState
}

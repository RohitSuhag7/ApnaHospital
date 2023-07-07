package com.example.apnahospital.repository

import com.example.apnahospital.model.Appointments
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class AppointmentsRepositoryImp @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : AppointmentsRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val dbReference: DatabaseReference
        get() = firebaseDatabase.reference.child(Constants.APPOINTMENTS)
            .child(currentUser?.uid.toString())

    override suspend fun setAppointment(appointments: Appointments?): FirebaseResponseState {
        return try {
            val result = dbReference.setValue(appointments)
            FirebaseResponseState.FirebaseSuccess(result)
        } catch (e: Exception) {
            FirebaseResponseState.FirebaseFailure(e)
        }
    }

    override suspend fun getAppointments(): FirebaseResponseState {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAppointments(): FirebaseResponseState {
        TODO("Not yet implemented")
    }

    override suspend fun updateAppointments(appointments: Appointments): FirebaseResponseState {
        TODO("Not yet implemented")
    }
}

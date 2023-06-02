package com.example.apnahospital.repository

import com.example.apnahospital.model.PatientInfo
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class PatientRepositoryImp @Inject constructor(private val firebaseDatabase: FirebaseDatabase, private val firebaseAuth: FirebaseAuth): PatientRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val dbReference: DatabaseReference
        get() = firebaseDatabase.reference

    override suspend fun updatePatientInfo(patientInfo: PatientInfo?): FirebaseResponseState {

        return try {
            val result = dbReference.child(Constants.patients).child(currentUser?.uid.toString()).setValue(patientInfo)
            FirebaseResponseState.FirebaseSuccess(result)
        } catch (e: Exception) {
            FirebaseResponseState.FirebaseFailure(e)
        }
    }

    override suspend fun getPatientInfo(patientInfo: PatientInfo?): FirebaseResponseState {
        TODO("Not yet implemented")
    }

    override suspend fun deletePatientInfo(patientInfo: PatientInfo?): FirebaseResponseState {
        TODO("Not yet implemented")
    }
}
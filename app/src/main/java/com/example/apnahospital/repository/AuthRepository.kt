package com.example.apnahospital.repository

import com.example.apnahospital.screenstate.FirebaseResponseState
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signIn(email: String, password: String): FirebaseResponseState

    suspend fun signUp(name: String, email: String, password: String): FirebaseResponseState

    fun signOut()

}

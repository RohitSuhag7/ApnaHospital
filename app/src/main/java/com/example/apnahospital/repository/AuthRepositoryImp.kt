package com.example.apnahospital.repository

import com.example.apnahospital.screenstate.FirebaseResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): FirebaseResponseState {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FirebaseResponseState.FirebaseSuccess(result.user)
        } catch (e: Exception) {
            FirebaseResponseState.FirebaseFailure(e)
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): FirebaseResponseState {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            FirebaseResponseState.FirebaseSuccess(result.user)
        } catch (e: Exception) {
            FirebaseResponseState.FirebaseFailure(e)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}

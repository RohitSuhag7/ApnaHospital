package com.example.apnahospital.screenstate

import java.lang.Exception

sealed class FirebaseResponseState {

    class FirebaseSuccess(val user: Any?): FirebaseResponseState()

    class FirebaseFailure(val exception: Exception): FirebaseResponseState()

    object FirebaseLoading: FirebaseResponseState()
}

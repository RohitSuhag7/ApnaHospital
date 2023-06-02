package com.example.apnahospital.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnahospital.repository.AuthRepository
import com.example.apnahospital.screenstate.FirebaseResponseState
import com.example.apnahospital.utils.Constants.IS_FIRST_TIME
import com.example.apnahospital.utils.readBool
import com.example.apnahospital.utils.writeBool
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val context: Context
) : ViewModel() {

    private val _signInStateFlow: MutableStateFlow<FirebaseResponseState?> = MutableStateFlow(null)
    val signInStateFlow: StateFlow<FirebaseResponseState?> = _signInStateFlow

    private val _signUpStateFlow: MutableStateFlow<FirebaseResponseState?> = MutableStateFlow(null)
    val signUpStateFlow: StateFlow<FirebaseResponseState?> = _signUpStateFlow

    private val currentUser: FirebaseUser?
        get() = authRepository.currentUser


    init {
        if (authRepository.currentUser != null) {
            _signInStateFlow.value = FirebaseResponseState.FirebaseSuccess(currentUser)
        }
    }

    fun signInUser(email: String, password: String) = viewModelScope.launch {
        _signInStateFlow.value = FirebaseResponseState.FirebaseLoading

        val result = authRepository.signIn(email, password)
        _signInStateFlow.value = result
    }

    fun signUpUser(name: String, email: String, password: String) = viewModelScope.launch {
        _signUpStateFlow.value = FirebaseResponseState.FirebaseLoading

        val result = authRepository.signUp(name, email, password)
        _signUpStateFlow.value = result
    }

    fun signOutUser() {
        authRepository.signOut()
        _signInStateFlow.value = null
    }

    fun isUserFirstTimeLogin(isFirst: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            context.writeBool(IS_FIRST_TIME, isFirst)
        }
    }

    val getIsUserFirstTimeLogin = viewModelScope.launch {
        context.readBool(IS_FIRST_TIME).collect {
       return@collect
        }
    }

}

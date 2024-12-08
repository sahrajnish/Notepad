package com.example.notepad.feature_app.presentation.welcomescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.feature_app.presentation.loginscreen.LoginState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val firebase: FirebaseAuth
): ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state = _state

    init {
        checkSignedInUser()
    }

    private fun checkSignedInUser() {
        viewModelScope.launch {
            val currentUser = firebase.currentUser
            if (currentUser != null && currentUser.isEmailVerified) {
                try {
                    _state.value = state.value.copy(
                        isLoginSuccess = true,
                        error = null
                    )
                } catch (e: Exception) {
                    _state.value = state.value.copy(
                        error = "Error: ${e.message}"
                    )
                }
            }
        }
    }
}
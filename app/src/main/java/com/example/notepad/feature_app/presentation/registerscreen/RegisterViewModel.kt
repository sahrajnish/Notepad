package com.example.notepad.feature_app.presentation.registerscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebase: FirebaseAuth
): ViewModel() {
    val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private var isTimerRunning = false

    fun onEvent(event: RegisterEvents) {
        when(event) {
            is RegisterEvents.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.text
                )
            }
            is RegisterEvents.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.text
                )
            }
            is RegisterEvents.OnRegisterClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    registerUser(
                        email = state.value.email,
                        password = state.value.password
                    )
                }
            }

            is RegisterEvents.OnResendClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    resendEmailVerification()
                }
            }

            is RegisterEvents.CoolDownTimer -> {
                if (!isTimerRunning) {
                    Log.d("timer", "RegisterViewModel1")
                    startCoolDownTimer()
                }
            }
        }
    }

    private fun resendEmailVerification() {
        if (!state.value.isResendAllowed) {
            _state.value = state.value.copy(
                error = "Please wait ${state.value.coolDownTime} seconds before resending the email.",
                message = null
            )
            return
        }

        val user = firebase.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { emailTask->
            if (emailTask.isSuccessful) {
                _state.value = state.value.copy(
                    message = "Verification email sent. Please verify your email.",
                    isLoading = false,
                    isResendAllowed = false,
                    error = null
                )
            } else  {
                _state.value = state.value.copy(
                    error = emailTask.exception?.message ?: "Failed to resend verification email.",
                    isLoading = false,
                    message = null
                )
            }
        }
    }

    private fun startCoolDownTimer() {
        Log.d("timer", "RegisterScreen2")
        isTimerRunning = true
        viewModelScope.launch {
            for (time in 60 downTo 1) {
                _state.value = state.value.copy(
                    coolDownTime = time
                )
                delay(1000)
            }
            _state.value = state.value.copy(
                isResendAllowed = true,
                error = null
            )
            isTimerRunning = false
        }
    }

    private fun registerUser(email: String, password: String) {
        if (!isEmailValid(email)) {
            _state.value = state.value.copy(
                error = "Invalid email format.",
                message = null,
                isLoading = false
            )
            return
        }

        if (!isPasswordValid(password)) {
            _state.value = state.value.copy(
                error = "Password must be at least 8 characters.",
                message = null,
                isLoading = false
            )
            return
        }

        firebase.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val user = firebase.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { emailTask->
                        if (emailTask.isSuccessful) {
                            _state.value = state.value.copy(
                                isLoading = false,
                                isLoginSuccess = false,
                                isEmailSent = true,
                                error = null,
                                message = "Verification email sent. Please verify your email."
                            )
                        } else {
                            _state.value = state.value.copy(
                                error = task.exception?.message ?: "Failed to send verification email.",
                                isLoginSuccess = false,
                                isLoading = false,
                                message = null
                            )
                        }
                    }
                } else {
                    _state.value = state.value.copy(
                        error = task.exception?.message ?: "Registration failed. Please try again.",
                        isLoading = false,
                        isLoginSuccess = false,
                        message = null
                    )
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}
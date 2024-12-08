package com.example.notepad.feature_app.presentation.loginscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val firebase: FirebaseAuth
): ViewModel() {
    val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private var isTimerRunning = false

    fun onEvent(event: LoginEvents) {
        when(event) {
            is LoginEvents.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.text
                )
            }
            is LoginEvents.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.text
                )
            }
            is LoginEvents.OnLoginClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    authenticateUser(
                        email = state.value.email,
                        password = state.value.password
                    )
                }
            }

            is LoginEvents.OnForgotPasswordClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true,
                        error = null
                    )
                    forgotPassword(email = state.value.email)
                }
            }

            is LoginEvents.CoolDownTimer -> {
                if (!isTimerRunning) {
                    startCoolDownTimer()
                }
            }

            is LoginEvents.OnResendClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    resendEmailVerification()
                }
            }
        }
    }

    private fun forgotPassword(email: String) {
        if (!isEmailValid(email)) {
            _state.value = state.value.copy(
                error = "Invalid email format.",
                isLoading = false,
                message = null
            )
            return
        }

        firebase.sendPasswordResetEmail(email).addOnCompleteListener { task->
            if (task.isSuccessful) {
                _state.value = state.value.copy(
                    message = "If this email is registered, a password reset email has been sent. Check your inbox.",
                    isLoading = false,
                    error = null
                )
            } else {
                val exception = task.exception
                if (exception is FirebaseAuthInvalidUserException) {
                    _state.value = state.value.copy(
                        error = task.exception?.message ?: "User not found.",
                        isLoading = false,
                        message = null
                    )
                } else {
                    _state.value = state.value.copy(
                        error = "Error sending password reset email.",
                        isLoading = false,
                        message = null
                    )
                }
            }
        }
    }

    private fun authenticateUser(email: String, password: String) {
        if (!isEmailValid(email)) {
            _state.value = state.value.copy(
                error = "Invalid email format.",
                isLoading = false,
                message = null
            )
            return
        }

        if (!isPasswordValid(password)) {
            _state.value = state.value.copy(
                error = "Password must be at least 8 characters.",
                isLoading = false,
                message = null
            )
            return
        }

        firebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val user = firebase.currentUser
                    if (user != null && user.isEmailVerified) {
                        _state.value = state.value.copy(
                            error = null,
                            isLoginSuccess = true,
                            isLoading = false,
                            message = null
                        )
                    } else {
                        _state.value = state.value.copy(
                            isLoading = false,
                            isEmailSent = true,
                            isResendAllowed = true,
                            isLoginSuccess = false,
                            error = "Please verify your email before logging in.",
                            message = null
                        )
                    }
                } else {
                    _state.value = state.value.copy(
                        error = task.exception?.message ?: "Login failed. Please try again.",
                        isLoading = false,
                        isLoginSuccess = false,
                        message = null
                    )
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
                    error = null,
                    message = "Verification email sent. Please verify your email.",
                    isLoading = false,
                    isResendAllowed = false
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
                error = null,
                message = null
            )
            isTimerRunning = false
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}
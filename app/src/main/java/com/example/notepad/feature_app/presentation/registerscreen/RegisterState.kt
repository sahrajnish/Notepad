package com.example.notepad.feature_app.presentation.registerscreen

data class RegisterState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isEmailSent: Boolean = false,
    val isResendAllowed: Boolean = false,
    val coolDownTime: Int = 60,
    val error: String? = null,
    val message: String? = null
)
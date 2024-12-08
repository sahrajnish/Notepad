package com.example.notepad.feature_app.presentation.loginscreen

data class LoginState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isResendAllowed: Boolean = true,
    val coolDownTime: Int = 60,
    val isEmailSent: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
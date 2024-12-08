package com.example.notepad.feature_app.presentation.loginscreen

import com.example.notepad.feature_app.presentation.registerscreen.RegisterEvents

sealed class LoginEvents {
    data class EnteredEmail(val text: String) : LoginEvents()
    data class EnteredPassword(val text: String): LoginEvents()
    object OnLoginClick: LoginEvents()
    object OnForgotPasswordClick: LoginEvents()
    object OnResendClick: LoginEvents()
    object CoolDownTimer: LoginEvents()
}
package com.example.notepad.feature_app.presentation.registerscreen

sealed class RegisterEvents {
    data class EnteredEmail(val text: String) : RegisterEvents()
    data class EnteredPassword(val text: String): RegisterEvents()
    object OnRegisterClick: RegisterEvents()
    object OnResendClick: RegisterEvents()
    object CoolDownTimer: RegisterEvents()
}
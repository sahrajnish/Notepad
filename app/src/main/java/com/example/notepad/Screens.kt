package com.example.notepad

sealed class Screens(val route: String) {
    object WelcomeScreen: Screens(route = "welcome_screen")
    object LoginScreen: Screens(route = "login_screen")
    object RegisterScreen: Screens(route = "register_screen")
    object NoteScreen: Screens(route = "home_screen")
    object AccountScreen: Screens(route = "account_screen")
    object AddNoteScreen: Screens(route = "add_note_screen")
}
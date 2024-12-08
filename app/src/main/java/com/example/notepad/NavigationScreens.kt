package com.example.notepad

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notepad.feature_app.presentation.accountscreen.AccountScreen
import com.example.notepad.feature_app.presentation.add_note_screen.AddNoteScreen
import com.example.notepad.feature_app.presentation.notescreen.NoteScreen
import com.example.notepad.feature_app.presentation.loginscreen.LoginScreen
import com.example.notepad.feature_app.presentation.registerscreen.RegisterScreen
import com.example.notepad.feature_app.presentation.welcomescreen.WelcomeScreen

@Composable
fun NavigationScreens(
    navController: NavHostController
) {
    Log.d("ab", "navigation1")
    NavHost(
        navController = navController,
        startDestination = Screens.WelcomeScreen.route,
        enterTransition = {
            fadeIn(animationSpec = tween(10))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(10))
        }
    ) {
        composable(route = Screens.WelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screens.LoginScreen.route) {
            Log.d("ab", "navigation2")
            LoginScreen(
                navController = navController
            )
        }
        composable(Screens.RegisterScreen.route) {
            Log.d("ab", "navigation3")
            RegisterScreen(navController = navController)
        }
        composable(Screens.NoteScreen.route) {
            NoteScreen(navController = navController)
        }
        composable(Screens.AccountScreen.route) {
            AccountScreen(navController = navController)
        }
        composable(Screens.AddNoteScreen.route) {
            AddNoteScreen()
        }
    }
}
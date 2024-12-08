package com.example.notepad.feature_app.presentation.accountscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notepad.Screens
import com.example.notepad.feature_app.presentation.components.WelcomeScreenButton
import com.example.notepad.feature_app.presentation.notescreen.NoteScreenEvents
import com.example.notepad.feature_app.presentation.notescreen.NoteViewModel

@Composable
fun AccountScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Scaffold  { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Account Information",
                fontSize = 24.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            val error = viewModel.state.value.error
            WelcomeScreenButton(
                onButtonClick = {
                    viewModel.onEvent(NoteScreenEvents.SignOut)
                    if (error == null) {
                        navController.navigate(Screens.WelcomeScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                text = "Sign out"
            )
            Spacer(Modifier.height(4.dp))
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
package com.example.notepad.feature_app.presentation.registerscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notepad.R
import com.example.notepad.Screens
import com.example.notepad.feature_app.presentation.components.AppTextField
import com.example.notepad.feature_app.presentation.components.WelcomeScreenButton

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isLoginSuccess) {
        if (viewModel.state.value.isLoginSuccess) {
            navController.navigate(Screens.NoteScreen.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Log.d("ab", "navigation4")
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.notepad_image),
            contentDescription = "Notepad Image",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.1f),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "It's good to see you.",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f)
                .padding(8.dp)
        ) {
            Text(
                text = "Create new account",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(4.dp))
            AppTextField(
                value = state.email,
                label = "Email",
                onValueChange = {
                    viewModel.onEvent(RegisterEvents.EnteredEmail(it))
                }
            )
            Spacer(Modifier.height(4.dp))
            AppTextField(
                value = state.password,
                label = "Password",
                onValueChange = {
                    viewModel.onEvent(RegisterEvents.EnteredPassword(it))
                },
                transformText = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = { viewModel.onEvent(RegisterEvents.OnRegisterClick) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = if(state.isLoading) {
                        Arrangement.SpaceBetween
                    } else {
                        Arrangement.Center
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Register",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = ProgressIndicatorDefaults.linearTrackColor
                        )
                    }
                }
            }

            if (state.isEmailSent) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (state.isResendAllowed) {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(RegisterEvents.OnResendClick)
                            }
                        ) {
                            Text(text = "Resend email.")
                        }
                    } else {
                        viewModel.onEvent(RegisterEvents.CoolDownTimer)
                        Text(
                            text = "Resend email in: ${state.coolDownTime}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            HorizontalDivider()

            Spacer(Modifier.height(4.dp))

            WelcomeScreenButton(
                onButtonClick = {
                    navController.navigate(Screens.LoginScreen.route)
                },
                text = "Already have an account"
            )

            Spacer(Modifier.height(4.dp))
            if (!state.error.isNullOrBlank()) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(4.dp))
            if (!state.message.isNullOrBlank()) {
                Text(
                    text = state.message!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
package com.example.notepad.feature_app.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notepad.R
import com.example.notepad.Screens
import com.example.notepad.feature_app.presentation.components.ContinueWithGoogle
import com.example.notepad.feature_app.presentation.components.WelcomeScreenButton

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.state.value.isLoginSuccess

    LaunchedEffect(state) {
        if (state) {
            navController.navigate(Screens.NoteScreen.route) {
                popUpTo(Screens.WelcomeScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.notepad_image),
            contentDescription = "Notepad Image",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        WelcomeScreenButton(
            onButtonClick = {
                navController.navigate(Screens.LoginScreen.route)
            },
            text = "Login with email"
        )
        Spacer(Modifier.height(8.dp))
        WelcomeScreenButton(
            onButtonClick = {
                navController.navigate(Screens.RegisterScreen.route)
            },
            text = "Register with email"
        )
        Spacer(Modifier.height(4.dp))

        HorizontalDivider(color = Color.Black)

        Spacer(Modifier.height(8.dp))

        ContinueWithGoogle(
            onClick = {  }
        )
    }

}
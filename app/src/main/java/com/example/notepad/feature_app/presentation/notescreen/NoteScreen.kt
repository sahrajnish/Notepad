package com.example.notepad.feature_app.presentation.notescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notepad.Screens
import com.example.notepad.feature_app.presentation.components.WelcomeScreenButton

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val currentDestination = remember { mutableStateOf(Screens.NoteScreen.route) }

    Scaffold (
        bottomBar = {
            NavigationBar {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "All Notes"
                        )
                    },
                    label = {
                        Text(text = "All notes")
                    },
                    selected = currentDestination.value == Screens.NoteScreen.route,
                    onClick = {
                        navController.navigate(Screens.NoteScreen.route) {
                            popUpTo(0) { inclusive = true}
                        }
                    }
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Note"
                        )
                    },
                    label = {
                        Text(text = "Add note")
                    },
                    selected = currentDestination.value == Screens.AddNoteScreen.route,
                    onClick = {

                    }
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ManageAccounts,
                            contentDescription = "Account"
                        )
                    },
                    label = {
                        Text(text = "Account")
                    },
                    selected = currentDestination.value == Screens.AccountScreen.route,
                    onClick = {
                        navController.navigate(Screens.AccountScreen.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Notes",
                fontSize = 24.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
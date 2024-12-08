package com.example.notepad.feature_app.presentation.notescreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.feature_app.domain.model.Note
import com.example.notepad.feature_app.domain.use_case.NoteUseCases
import com.example.notepad.feature_app.presentation.loginscreen.LoginState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val firebase: FirebaseAuth,
    private val noteUseCases: NoteUseCases
): ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state = _state

    fun onEvent(event: NoteScreenEvents) {
        when(event) {
            is NoteScreenEvents.SignOut -> {
                viewModelScope.launch {
                    try {
                        firebase.signOut()
                        _state.value = state.value.copy(
                            email = "",
                            password = "",
                            isLoading = false,
                            isLoginSuccess = false,
                            error = null,
                            message = null
                        )
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            error = "Error: ${e.message}"
                        )
                    }
                }
            }
        }
    }

    init {
        val note = Note(
            title = "ABC",
            content = "abc",
            timestamp = Timestamp.now(),
            colorInt = Color.Cyan.toArgb()
        )
        viewModelScope.launch {
            noteUseCases.insertNote(note)
        }
    }
}
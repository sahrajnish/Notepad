package com.example.notepad.feature_app.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.firebase.Timestamp

data class Note(
    val title: String,
    val content: String,
    val timestamp: Timestamp?,
    val colorInt: Int
) {
    companion object {
        val noteColor = listOf(Color.Red, Color.Yellow, Color.Green)
    }

    fun fromInt(colorInt: Int): Color {
        return Color(colorInt)
    }

    fun toInt(color: Color): Int {
        return color.toArgb()
    }
}
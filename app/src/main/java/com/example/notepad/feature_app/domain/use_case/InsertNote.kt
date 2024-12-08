package com.example.notepad.feature_app.domain.use_case

import com.example.notepad.feature_app.domain.model.Note
import com.example.notepad.feature_app.domain.repository.NoteRepository

class InsertNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        return repository.insertNote(note)
    }
}
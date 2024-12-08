package com.example.notepad.feature_app.data.repository

import com.example.notepad.feature_app.domain.model.Note
import com.example.notepad.feature_app.domain.repository.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class NoteRepositoryImp(
    private val db: FirebaseDatabase,
    private val firebase: FirebaseAuth
) : NoteRepository {


    override fun getNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: String): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note) {
        val currentUser = firebase.currentUser
        val userRef = db.getReference("Users/${currentUser?.uid}").push()
        val update = mapOf(
            "title" to note.title,
            "content" to note.content,
            "date" to note.timestamp?.toDate().toString(),
            "noteColor" to note.colorInt
        )
        userRef.setValue(update).addOnCompleteListener { task->
            if (task.isSuccessful) {
                println("Note inserted successfully")
            } else {
                println("Failed to insert note: ${task.exception?.message}")
            }
        }.await()
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }
}
package com.example.notepad.di

import com.example.notepad.feature_app.data.repository.NoteRepositoryImp
import com.example.notepad.feature_app.domain.repository.NoteRepository
import com.example.notepad.feature_app.domain.use_case.InsertNote
import com.example.notepad.feature_app.domain.use_case.NoteUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = Firebase.database

    @Provides
    @Singleton
    fun provideNoteRepository(db: FirebaseDatabase, firebase: FirebaseAuth): NoteRepository {
        return NoteRepositoryImp(db = db, firebase = firebase)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            insertNote = InsertNote(repository)
        )
    }
}
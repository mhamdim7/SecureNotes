package com.sameh.securenotes.di

import android.app.Application
import com.sameh.securenotes.feature_note.data.database.DatabaseHelper
import com.sameh.securenotes.feature_note.data.database.NoteDao
import com.sameh.securenotes.feature_note.data.database.NoteDatabase
import com.sameh.securenotes.feature_note.data.repository.NoteRepositoryImpl
import com.sameh.securenotes.feature_note.data.sources.framework.AndroidResourceProvider
import com.sameh.securenotes.feature_note.data.sources.local.LocalDataSource
import com.sameh.securenotes.feature_note.domain.mapper.NoteEntityMapper
import com.sameh.securenotes.feature_note.domain.repository.NoteRepository
import com.sameh.securenotes.feature_note.domain.usecases.AddNote
import com.sameh.securenotes.feature_note.domain.usecases.DeleteNote
import com.sameh.securenotes.feature_note.domain.usecases.GetNote
import com.sameh.securenotes.feature_note.domain.usecases.GetNotes
import com.sameh.securenotes.feature_note.domain.usecases.NoteUseCases
import com.sameh.securenotes.feature_note.presentation.mapper.NoteUiModelMapper
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
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return DatabaseHelper(app.applicationContext).database
    }

    @Provides
    @Singleton
    fun provideNodeDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(noteDao: NoteDao): LocalDataSource {
        return LocalDataSource(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUiModelMapper(): NoteUiModelMapper {
        return NoteUiModelMapper()
    }

    @Provides
    @Singleton
    fun provideNoteEntityMapper(): NoteEntityMapper {
        return NoteEntityMapper()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        localDataSource: LocalDataSource,
        noteEntityMapper: NoteEntityMapper
    ): NoteRepository {
        return NoteRepositoryImpl(localDataSource, noteEntityMapper)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(app: Application): AndroidResourceProvider {
        return AndroidResourceProvider(app.resources)
    }

    @Provides
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            addNote = AddNote(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }
}
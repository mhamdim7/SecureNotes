package com.sameh.securenotes.di

import android.app.Application
import com.sameh.securenotes.adapter.framework.AndroidResourceProvider
import com.sameh.securenotes.adapter.mapper.DomainUiMapper
import com.sameh.securenotes.adapter.mapper.EntityDomainMapper
import com.sameh.securenotes.adapter.persistence.DatabaseHelper
import com.sameh.securenotes.adapter.persistence.NoteDao
import com.sameh.securenotes.adapter.persistence.NoteDatabase
import com.sameh.securenotes.adapter.persistence.RoomNoteRepository
import com.sameh.securenotes.application.port.inbound.AddNote
import com.sameh.securenotes.application.port.inbound.DeleteNote
import com.sameh.securenotes.application.port.inbound.GetNote
import com.sameh.securenotes.application.port.inbound.GetNotes
import com.sameh.securenotes.application.port.inbound.NoteUseCases
import com.sameh.securenotes.domain.repository.NoteRepository
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
    fun provideNoteUiModelMapper(): DomainUiMapper {
        return DomainUiMapper()
    }

    @Provides
    @Singleton
    fun provideNoteEntityMapper(): EntityDomainMapper {
        return EntityDomainMapper()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao,
        entityDomainMapper: EntityDomainMapper
    ): NoteRepository {
        return RoomNoteRepository(noteDao, entityDomainMapper)
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
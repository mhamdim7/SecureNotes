package com.sameh.securenotes.feature_note.data.repository

import com.sameh.securenotes.feature_note.data.sources.local.LocalDataSource
import com.sameh.securenotes.feature_note.domain.mapper.NoteEntityMapper
import com.sameh.securenotes.feature_note.domain.model.Note
import com.sameh.securenotes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val source: LocalDataSource,
    private val mapper: NoteEntityMapper
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = source.getNotes().map {
        it.map { entity -> mapper.mapFrom(entity) }
    }

    override suspend fun getNoteById(id: Int): Note? =
        mapper.mapFrom(source.getNoteById(id))

    override suspend fun insertOrUpdateNote(note: Note) =
        source.insertOrUpdateNote(mapper.mapTo(note))

    override suspend fun deleteNote(note: Note) = source.deleteNote(mapper.mapTo(note))
}
package com.sameh.securenotes.adapter.persistence

import com.sameh.securenotes.adapter.mapper.EntityDomainMapper
import com.sameh.securenotes.domain.model.Note
import com.sameh.securenotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteRepository(
    private val dao: NoteDao,
    private val mapper: EntityDomainMapper
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = dao.getNotes().map {
        it.map { entity -> mapper.mapFrom(entity) }
    }

    override suspend fun getNoteById(id: Int): Note? =
        mapper.mapFrom(dao.getNoteById(id))

    override suspend fun insertOrUpdateNote(note: Note) =
        dao.insertOrUpdateNote(mapper.mapTo(note))

    override suspend fun deleteNote(note: Note) = dao.deleteNote(mapper.mapTo(note))
}
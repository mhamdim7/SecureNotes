package com.sameh.securenotes.feature_note.data.sources.local

import com.sameh.securenotes.feature_note.data.database.NoteDao
import com.sameh.securenotes.feature_note.data.database.NoteEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: NoteDao) {
    fun getNotes(): Flow<List<NoteEntity>> = dao.getNotes()

    suspend fun getNoteById(id: Int): NoteEntity? = dao.getNoteById(id)

    suspend fun insertOrUpdateNote(note: NoteEntity) = dao.insertOrUpdateNote(note)

    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)
}
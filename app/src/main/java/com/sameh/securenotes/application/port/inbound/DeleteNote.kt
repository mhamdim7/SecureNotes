package com.sameh.securenotes.application.port.inbound

import com.sameh.securenotes.domain.model.Note
import com.sameh.securenotes.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}
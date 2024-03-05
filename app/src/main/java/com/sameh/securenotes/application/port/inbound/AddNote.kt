package com.sameh.securenotes.application.port.inbound

import com.sameh.securenotes.domain.model.Note
import com.sameh.securenotes.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        note.validate()
        repository.insertOrUpdateNote(
            note.copy(
                title = note.title.trim(),
                content = note.content.trim()
            )
        )
    }
}
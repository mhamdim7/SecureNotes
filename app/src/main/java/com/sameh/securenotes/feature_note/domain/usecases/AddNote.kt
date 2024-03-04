package com.sameh.securenotes.feature_note.domain.usecases

import com.sameh.securenotes.feature_note.domain.model.InvalidNoteException
import com.sameh.securenotes.feature_note.domain.model.Note
import com.sameh.securenotes.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank() && note.content.isBlank()) {
            throw InvalidNoteException("The note must have a value.")
        }
        repository.insertOrUpdateNote(
            note.copy(
                title = note.title.trim(),
                content = note.content.trim()
            )
        )
    }
}
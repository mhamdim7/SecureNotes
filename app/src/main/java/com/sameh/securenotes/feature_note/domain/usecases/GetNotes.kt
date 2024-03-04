package com.sameh.securenotes.feature_note.domain.usecases

import com.sameh.securenotes.feature_note.domain.repository.NoteRepository

class GetNotes(private val repository: NoteRepository) {

    operator fun invoke() = repository.getNotes()

}
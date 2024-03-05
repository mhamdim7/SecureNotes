package com.sameh.securenotes.application.port.inbound

import com.sameh.securenotes.domain.model.Note
import com.sameh.securenotes.domain.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}
package com.sameh.securenotes.application.port.inbound

import com.sameh.securenotes.domain.repository.NoteRepository

class GetNotes(private val repository: NoteRepository) {

    operator fun invoke() = repository.getNotes()

}
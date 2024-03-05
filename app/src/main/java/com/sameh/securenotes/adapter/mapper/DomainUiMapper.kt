package com.sameh.securenotes.adapter.mapper

import com.sameh.securenotes.domain.model.Note
import com.sameh.securenotes.adapter.ui.uimodel.NoteUiModel

class DomainUiMapper : Mapper<Note, NoteUiModel> {
    override fun mapFrom(from: Note): NoteUiModel {
        return NoteUiModel(
            id = from.id,
            title = from.title,
            content = from.content,
            timestamp = from.timestamp,
            color = from.color
        )
    }


    override fun mapTo(to: NoteUiModel): Note {
        return Note(
            id = to.id,
            title = to.title,
            content = to.content,
            timestamp = to.timestamp,
            color = to.color
        )
    }

    // Overloaded version for nullable input
    @JvmName("mapToNullable")
    fun mapTo(to: NoteUiModel?): Note? = to?.let {
        mapTo(it)
    }
}
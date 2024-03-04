package com.sameh.securenotes.feature_note.presentation.mapper

import com.sameh.securenotes.feature_note.common.Mapper
import com.sameh.securenotes.feature_note.domain.model.Note
import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel

class NoteUiModelMapper : Mapper<Note, NoteUiModel> {
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
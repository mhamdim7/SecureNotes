package com.sameh.securenotes.feature_note.domain.mapper

import com.sameh.securenotes.feature_note.common.Mapper
import com.sameh.securenotes.feature_note.data.database.NoteEntity
import com.sameh.securenotes.feature_note.domain.model.Note

class NoteEntityMapper : Mapper<NoteEntity, Note> {
    override fun mapFrom(from: NoteEntity): Note {
        return Note(
            id = from.id,
            title = from.title,
            content = from.content,
            timestamp = from.timestamp,
            color = from.color
        )
    }

    // Overloaded version for nullable input
    @JvmName("mapFromNullable")
    fun mapFrom(from: NoteEntity?): Note? = from?.let {
        mapFrom(it)
    }

    override fun mapTo(to: Note): NoteEntity {
        return NoteEntity(
            id = to.id,
            title = to.title,
            content = to.content,
            timestamp = to.timestamp,
            color = to.color
        )
    }
}
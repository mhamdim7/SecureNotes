package com.sameh.securenotes.adapter.mapper

import com.sameh.securenotes.adapter.persistence.NoteEntity
import com.sameh.securenotes.domain.model.Note

class EntityDomainMapper : Mapper<NoteEntity, Note> {
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
package com.sameh.securenotes.feature_note.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String = "",
    val content: String = "",
    val timestamp: Long,
    val color: Int,
)
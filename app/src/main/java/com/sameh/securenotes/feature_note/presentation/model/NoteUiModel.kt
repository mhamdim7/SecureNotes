package com.sameh.securenotes.feature_note.presentation.model


data class NoteUiModel(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long,
    val color: Int
)
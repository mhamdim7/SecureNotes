package com.sameh.securenotes.adapter.ui.uimodel


data class NoteUiModel(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long,
    val color: Int
)
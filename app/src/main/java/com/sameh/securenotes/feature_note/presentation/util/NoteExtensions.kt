package com.sameh.securenotes.feature_note.presentation.util

import com.sameh.securenotes.feature_note.domain.util.NoteOrder
import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel

fun List<NoteUiModel>.sortByOrder(noteOrder: NoteOrder): List<NoteUiModel> {
    return when (noteOrder) {
        is NoteOrder.Title -> {
            if (noteOrder.isAscending()) sortedBy { it.title.lowercase() }
            else sortedByDescending { it.title.lowercase() }
        }

        is NoteOrder.Date -> {
            if (noteOrder.isAscending()) sortedBy { it.timestamp }
            else sortedByDescending { it.timestamp }
        }

        is NoteOrder.Color -> {
            if (noteOrder.isAscending()) sortedBy { it.color }
            else sortedByDescending { it.color }
        }
    }
}
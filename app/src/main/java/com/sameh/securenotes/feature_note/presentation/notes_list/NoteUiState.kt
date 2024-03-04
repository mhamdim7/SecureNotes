package com.sameh.securenotes.feature_note.presentation.notes_list

import com.sameh.securenotes.feature_note.domain.util.NoteOrder
import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel

data class NoteUiState(
    val notes: List<NoteUiModel> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.DEFAULT_ORDER,
    val showOrderPopUp: Boolean = false
)

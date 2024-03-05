package com.sameh.securenotes.adapter.ui.notes_list

import com.sameh.securenotes.adapter.ui.uimodel.NoteOrder
import com.sameh.securenotes.adapter.ui.uimodel.NoteUiModel

data class NoteUiState(
    val notes: List<NoteUiModel> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.DEFAULT_ORDER,
    val showOrderPopUp: Boolean = false
)

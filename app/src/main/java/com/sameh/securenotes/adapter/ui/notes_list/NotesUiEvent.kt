package com.sameh.securenotes.adapter.ui.notes_list

import com.sameh.securenotes.adapter.ui.uimodel.NoteOrder
import com.sameh.securenotes.adapter.ui.uimodel.NoteUiModel

sealed class NotesUiEvent {
    data class Order(val noteOrder: NoteOrder) : NotesUiEvent()
    data class DeleteNote(val note: NoteUiModel) : NotesUiEvent()
    data object RestoreNote : NotesUiEvent()
    data object ToggleOrderPopUp : NotesUiEvent()
}

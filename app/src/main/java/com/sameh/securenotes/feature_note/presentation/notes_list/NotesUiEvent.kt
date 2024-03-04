package com.sameh.securenotes.feature_note.presentation.notes_list

import com.sameh.securenotes.feature_note.domain.util.NoteOrder
import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel

sealed class NotesUiEvent {
    data class Order(val noteOrder: NoteOrder) : NotesUiEvent()
    data class DeleteNote(val note: NoteUiModel) : NotesUiEvent()
    data object RestoreNote : NotesUiEvent()
    data object ToggleOrderPopUp : NotesUiEvent()
}

package com.sameh.securenotes.feature_note.presentation.add_edit_note

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}

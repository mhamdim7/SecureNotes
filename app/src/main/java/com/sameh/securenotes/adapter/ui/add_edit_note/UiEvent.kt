package com.sameh.securenotes.adapter.ui.add_edit_note

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}

package com.sameh.securenotes.ui.navigation

import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel

sealed class Routes(val route: String) {
    data object NotesScreen : Routes("notes_screen")
    data class AddEditNoteScreen(val note: NoteUiModel?) : Routes(getRoute(note)) {

        companion object {
            private const val baseRoute = "add_edit_note_screen"
            const val idArgKey = "noteId"
            const val colorArgKey = "noteColor"

            fun getRoute(note: NoteUiModel?) =
                baseRoute + (note?.let { "?$idArgKey=${it.id}&$colorArgKey=${it.color}" }
                    ?: "?$idArgKey={$idArgKey}&$colorArgKey={$colorArgKey}")
        }
    }
}

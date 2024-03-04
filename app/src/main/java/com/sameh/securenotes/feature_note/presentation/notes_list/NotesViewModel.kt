package com.sameh.securenotes.feature_note.presentation.notes_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameh.securenotes.feature_note.domain.usecases.NoteUseCases
import com.sameh.securenotes.feature_note.presentation.mapper.NoteUiModelMapper
import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel
import com.sameh.securenotes.feature_note.presentation.util.sortByOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val mapper: NoteUiModelMapper,
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteUiState())
    val state: State<NoteUiState> = _state

    private var recentlyDeletedNote: NoteUiModel? = null

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    fun onEvent(notesEvent: NotesUiEvent) {
        when (notesEvent) {
            is NotesUiEvent.Order -> {
                updateNotesOrder(notesEvent)
            }

            is NotesUiEvent.DeleteNote -> {
                deleteNote(notesEvent)
            }

            is NotesUiEvent.RestoreNote -> {
                undoDeleteNote()
            }

            is NotesUiEvent.ToggleOrderPopUp -> {
                toggleOrderSectionPopUp()
            }
        }
    }

    private fun toggleOrderSectionPopUp() {
        _state.value = state.value.copy(
            showOrderPopUp = !state.value.showOrderPopUp
        )
    }

    private fun undoDeleteNote() {
        viewModelScope.launch {
            noteUseCases.addNote(mapper.mapTo(recentlyDeletedNote) ?: return@launch)
            recentlyDeletedNote = null
        }
    }

    private fun deleteNote(notesEvent: NotesUiEvent.DeleteNote) {
        viewModelScope.launch {
            recentlyDeletedNote = notesEvent.note
            noteUseCases.deleteNote(mapper.mapTo(notesEvent.note))
        }
    }

    private fun updateNotesOrder(notesEvent: NotesUiEvent.Order) {
        val currentOrder = state.value.noteOrder
        val newOrder = notesEvent.noteOrder

        // Check if the order actually needs updating to avoid unnecessary operations.
        if (currentOrder != newOrder) {
            // Update the note order and sort the notes in a single state update to minimize recompositions.
            val sortedNotes = _state.value.notes.sortByOrder(newOrder)
            _state.value = _state.value.copy(
                noteOrder = newOrder,
                notes = sortedNotes
            )
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes().onEach { notes ->
            val uiModelNotes = notes.map { mapper.mapFrom(it) }
            _state.value = state.value.copy(
                notes = uiModelNotes.sortByOrder(state.value.noteOrder)
            )
        }.launchIn(viewModelScope)
    }

}
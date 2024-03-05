package com.sameh.securenotes.adapter.ui.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameh.securenotes.R
import com.sameh.securenotes.adapter.framework.AndroidResourceProvider
import com.sameh.securenotes.adapter.mapper.DomainUiMapper
import com.sameh.securenotes.application.port.inbound.NoteUseCases
import com.sameh.securenotes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val mapper: DomainUiMapper,
    private val resources: AndroidResourceProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = resources.getString(R.string.enter_title)
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle
    private val _noteContent = mutableStateOf(
        NoteTextFieldState(hint = resources.getString(R.string.enter_some_content))
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableIntStateOf(
        savedStateHandle.get<Int>(resources.getString(R.string.notecolor)).let {
            if (it == null || it == -1) Note.noteColors.random().toArgb() else it
        }
    )
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>(resources.getString(R.string.noteid))?.let { noteId ->
            populateExistingNote(noteId)
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ColorChange -> _noteColor.intValue = event.color

            is AddEditNoteEvent.ContentFocusChange -> {
                val showHint = !event.focusState.isFocused && noteContent.value.text.isBlank()
                _noteContent.value = noteContent.value.copy(isHintVisible = showHint)
            }

            is AddEditNoteEvent.ContentValueChange -> {
                _noteContent.value = noteContent.value.copy(text = event.value)
            }

            is AddEditNoteEvent.SaveNote -> saveNote()

            is AddEditNoteEvent.TitleFocusChange -> {
                val showHint = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                _noteTitle.value = noteTitle.value.copy(isHintVisible = showHint)
            }

            is AddEditNoteEvent.TitleValueChange -> {
                _noteTitle.value = noteTitle.value.copy(text = event.value)
            }
        }
    }

    private fun saveNote() = viewModelScope.launch {
        runCatching {
            val note = Note(
                title = noteTitle.value.text,
                content = noteContent.value.text,
                timestamp = System.currentTimeMillis(),
                color = noteColor.value,
                id = currentNoteId
            )
            // TODO: check if not exists / wasn't updated by ui
            noteUseCases.addNote(note)
            _eventFlow.emit(UiEvent.ShowSnackBar(resources.getString(R.string.note_saved)))
        }.onFailure {
            _eventFlow.emit(
                UiEvent.ShowSnackBar(
                    it.message ?: resources.getString(R.string.couldn_t_save_the_note)
                )
            )
        }
    }


    private fun populateExistingNote(noteId: Int) = viewModelScope.launch {
        noteUseCases.getNote(noteId)?.let {
            val noteUiModel = mapper.mapFrom(it)
            currentNoteId = noteUiModel.id
            _noteTitle.value = noteTitle.value.copy(
                text = noteUiModel.title,
                isHintVisible = noteUiModel.title.isBlank()
            )
            _noteContent.value = noteContent.value.copy(
                text = noteUiModel.content,
                isHintVisible = noteUiModel.content.isBlank()
            )
            _noteColor.intValue = noteUiModel.color
        }
    }
}

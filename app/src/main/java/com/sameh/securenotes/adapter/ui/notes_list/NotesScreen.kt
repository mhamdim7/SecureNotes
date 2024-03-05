package com.sameh.securenotes.adapter.ui.notes_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sameh.securenotes.R
import com.sameh.securenotes.adapter.ui.uimodel.NoteUiModel
import com.sameh.securenotes.adapter.ui.notes_list.components.AddNotesButton
import com.sameh.securenotes.adapter.ui.notes_list.components.EmptyListText
import com.sameh.securenotes.adapter.ui.notes_list.components.NotesList
import com.sameh.securenotes.adapter.ui.notes_list.components.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    onNavigateToAddEditScreen: (note: NoteUiModel?) -> Unit
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                state = state,
                onSortButtonClicked = {
                    viewModel.onEvent(NotesUiEvent.ToggleOrderPopUp)
                },
                onSortOrderChanged = {
                    viewModel.onEvent(NotesUiEvent.Order(it))
                }
            )
        },
        floatingActionButton = {
            AddNotesButton(onNavigateToAddEditScreen)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState, modifier = Modifier) },
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (state.notes.isEmpty())
                EmptyListText()
            else {
                val message = stringResource(R.string.note_deleted)
                val actionLabel = stringResource(R.string.undo)

                NotesList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    state = state,
                    onNavigateToAddEditScreen = onNavigateToAddEditScreen
                ) {
                    snackbarDeleteWithUndo(
                        scope = scope,
                        snackbarState = snackbarState,
                        message = message,
                        actionLabel = actionLabel,
                        deleteNote = { viewModel.onEvent(NotesUiEvent.DeleteNote(it)) },
                        restoreNote = { viewModel.onEvent(NotesUiEvent.RestoreNote) }
                    )
                }
            }

        }
    }
}

fun snackbarDeleteWithUndo(
    scope: CoroutineScope,
    snackbarState: SnackbarHostState,
    message: String,
    actionLabel: String,
    deleteNote: () -> Unit,
    restoreNote: () -> Unit,
) {
    deleteNote()
    scope.launch {
        val result = snackbarState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long
        )
        if (result == SnackbarResult.ActionPerformed) {
            restoreNote()
        }
    }
}

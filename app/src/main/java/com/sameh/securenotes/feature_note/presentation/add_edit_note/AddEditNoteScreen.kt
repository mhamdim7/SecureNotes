package com.sameh.securenotes.feature_note.presentation.add_edit_note

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.sameh.securenotes.feature_note.domain.model.Note
import com.sameh.securenotes.feature_note.presentation.add_edit_note.components.ColorChooser
import com.sameh.securenotes.feature_note.presentation.add_edit_note.components.TransparentTextField
import com.sameh.securenotes.feature_note.presentation.util.plus
import com.sameh.securenotes.ui.theme.dimens
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value
    val colorState = viewModel.noteColor.value
    val color = animateColorAsState(
        targetValue = Color(colorState),
        animationSpec = tween(durationMillis = 500), label = ""
    )

    val snackBarState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarState, modifier = Modifier) }
    ) { paddingValues ->
        LaunchedEffect(key1 = Unit) {
            viewModel.eventFlow.collectLatest {
                when (it) {
                    is UiEvent.ShowSnackBar -> {
                        snackBarState.showSnackbar(it.message)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color.value)
                .padding(paddingValues + PaddingValues(MaterialTheme.dimens.medium))
        ) {
            ColorChooser(
                Note.noteColors,
                Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small)
            ) {
                viewModel.onEvent(AddEditNoteEvent.ColorChange(it.toArgb()))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            TransparentTextField(
                value = noteTitle.text,
                hint = noteTitle.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleValueChange(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleFocusChange(it))
                },
                isHintVisible = noteTitle.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineLarge,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                value = noteContent.text,
                hint = noteContent.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentValueChange(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentFocusChange(it))
                },
                isHintVisible = noteContent.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }

    // Lifecycle event observation to save note when the app goes to background
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

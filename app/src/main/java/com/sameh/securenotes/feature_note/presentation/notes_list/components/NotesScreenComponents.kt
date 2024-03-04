package com.sameh.securenotes.feature_note.presentation.notes_list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.sameh.securenotes.R
import com.sameh.securenotes.feature_note.domain.util.NoteOrder
import com.sameh.securenotes.feature_note.presentation.model.NoteUiModel
import com.sameh.securenotes.feature_note.presentation.notes_list.NoteUiState
import com.sameh.securenotes.ui.theme.dimens


@Composable
fun EmptyListText() {
    Text(
        text = stringResource(R.string.your_notes_list_is_empty),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: NoteUiState,
    onSortButtonClicked: () -> Unit,
    onSortOrderChanged: (NoteOrder) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.your_notes),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            if (state.notes.isNotEmpty()) {
                IconButton(onClick = {
                    onSortButtonClicked()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
                DropdownMenu(
                    expanded = state.showOrderPopUp,
                    onDismissRequest = { onSortButtonClicked() }
                ) {
                    OrderSelectionMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimens.medium), noteOrder = state.noteOrder
                    ) { noteOrder -> onSortOrderChanged(noteOrder) }
                }
            }
        }
    )
}

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    state: NoteUiState,
    onNavigateToAddEditScreen: (note: NoteUiModel?) -> Unit,
    onDeleteClicked: (NoteUiModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.dimens.medium)
    ) {
        items(state.notes) { note ->
            NoteItem(note = note, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onNavigateToAddEditScreen(note)
                },
                onDeleteClick = { onDeleteClicked(note) })
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
        }
    }
}


@Composable
fun NoteItem(
    note: NoteUiModel,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = MaterialTheme.dimens.medium,
    cutCornerSize: Dp = MaterialTheme.dimens.veryLarge,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    size = size
                )
                drawRoundRect(
                    color = Color.Black,
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100),
                    alpha = 0.2f,
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.medium)
                .padding(end = MaterialTheme.dimens.large)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete_note),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun AddNotesButton(onNavigateToAddEditScreen: (note: NoteUiModel?) -> Unit) {
    FloatingActionButton(
        onClick = {
            onNavigateToAddEditScreen(null)
        }, containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_note)
        )
    }
}
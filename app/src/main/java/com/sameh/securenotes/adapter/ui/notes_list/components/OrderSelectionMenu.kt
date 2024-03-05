package com.sameh.securenotes.adapter.ui.notes_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sameh.securenotes.R
import com.sameh.securenotes.adapter.ui.uimodel.NoteOrder
import com.sameh.securenotes.adapter.ui.uimodel.OrderType
import com.sameh.securenotes.adapter.ui.theme.dimens

@Composable
fun OrderSelectionMenu(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.DEFAULT_ORDER,
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.title),
                selected = noteOrder is NoteOrder.Title,
                onSelected = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
            DefaultRadioButton(
                text = stringResource(R.string.date),
                selected = noteOrder is NoteOrder.Date,
                onSelected = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
            DefaultRadioButton(
                text = stringResource(R.string.color),
                selected = noteOrder is NoteOrder.Color,
                onSelected = {
                    onOrderChange(NoteOrder.Color(noteOrder.orderType))
                }
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelected = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending,
                onSelected = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}
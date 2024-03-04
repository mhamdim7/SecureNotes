package com.sameh.securenotes.feature_note.domain.model

import com.sameh.securenotes.ui.theme.BabyBlue
import com.sameh.securenotes.ui.theme.LightGreen
import com.sameh.securenotes.ui.theme.RedOrange
import com.sameh.securenotes.ui.theme.RedPink
import com.sameh.securenotes.ui.theme.Violet

data class Note(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long,
    val color: Int
) {

    companion object {
        val noteColors = listOf(
            RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)

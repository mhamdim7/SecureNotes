package com.sameh.securenotes.domain.model

import com.sameh.securenotes.adapter.ui.theme.BabyBlue
import com.sameh.securenotes.adapter.ui.theme.LightGreen
import com.sameh.securenotes.adapter.ui.theme.RedOrange
import com.sameh.securenotes.adapter.ui.theme.RedPink
import com.sameh.securenotes.adapter.ui.theme.Violet

data class Note(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long,
    val color: Int
) {
    fun validate() {
        if (title.isBlank() && content.isBlank()) {
            throw InvalidNoteException("The note must have a value.")
        }
    }

    companion object {
        val noteColors = listOf(
            RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)

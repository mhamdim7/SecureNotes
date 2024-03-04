package com.sameh.securenotes.feature_note.domain.util

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
}
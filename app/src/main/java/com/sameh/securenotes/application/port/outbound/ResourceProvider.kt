package com.sameh.securenotes.application.port.outbound

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
}
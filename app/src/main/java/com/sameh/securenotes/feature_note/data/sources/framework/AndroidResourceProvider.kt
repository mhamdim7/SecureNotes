package com.sameh.securenotes.feature_note.data.sources.framework

import android.content.res.Resources
import androidx.annotation.StringRes
import com.sameh.securenotes.feature_note.domain.util.ResourceProvider

class AndroidResourceProvider(private val resources: Resources) : ResourceProvider {
    override fun getString(@StringRes id: Int): String = resources.getString(id)
}
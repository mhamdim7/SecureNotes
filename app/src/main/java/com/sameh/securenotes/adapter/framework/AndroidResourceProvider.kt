package com.sameh.securenotes.adapter.framework

import android.content.res.Resources
import androidx.annotation.StringRes
import com.sameh.securenotes.application.port.outbound.ResourceProvider

class AndroidResourceProvider(private val resources: Resources) : ResourceProvider {
    override fun getString(@StringRes id: Int): String = resources.getString(id)
}
package com.sameh.securenotes.feature_note.common

interface Mapper<From, To> {
    fun mapFrom(from: From): To
    fun mapTo(to: To): From
}
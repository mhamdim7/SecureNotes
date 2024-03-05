package com.sameh.securenotes.adapter.mapper

interface Mapper<From, To> {
    fun mapFrom(from: From): To
    fun mapTo(to: To): From
}
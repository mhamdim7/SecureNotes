package com.sameh.securenotes.adapter.ui.uimodel

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}

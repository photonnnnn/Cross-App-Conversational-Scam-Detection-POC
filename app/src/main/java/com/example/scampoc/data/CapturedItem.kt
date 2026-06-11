package com.example.scampoc.data

data class CapturedItem(
    val source: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

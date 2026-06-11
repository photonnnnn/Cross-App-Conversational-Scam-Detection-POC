package com.example.scampoc.data

import java.util.concurrent.CopyOnWriteArrayList

object CaptureRepository {
    val items = CopyOnWriteArrayList<CapturedItem>()

    fun add(item: CapturedItem) {
        items.add(0, item) // Add to the beginning of the list
    }
}

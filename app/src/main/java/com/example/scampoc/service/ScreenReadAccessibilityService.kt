package com.example.scampoc.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.example.scampoc.data.CaptureRepository
import com.example.scampoc.data.CapturedItem
import com.example.scampoc.util.TreeParser

import android.util.Log

class ScreenReadAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("ScreenReadService", "Event received: ${event?.eventType}")
        if (event == null) return

        if ((event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) ||
            (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
        ) {
            val root = rootInActiveWindow ?: return
            val text = TreeParser.extractText(root).trim()

            if (text.isNotBlank()) {
                CaptureRepository.add(
                    CapturedItem(
                        source = event.packageName?.toString() ?: "unknown",
                        text = text,
                    ),
                )
            }
        }
    }

    override fun onInterrupt() {}

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("ScreenReadService", "Service Connected and Ready")
    }
}

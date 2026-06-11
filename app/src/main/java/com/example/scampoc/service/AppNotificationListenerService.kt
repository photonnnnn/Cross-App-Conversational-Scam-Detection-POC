package com.example.scampoc.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.scampoc.data.CaptureRepository
import com.example.scampoc.data.CapturedItem

class AppNotificationListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) return

        val extras = sbn.notification.extras
        val title = extras.getCharSequence("android.title")?.toString().orEmpty()
        val text = extras.getCharSequence("android.text")?.toString().orEmpty()
        val combined = listOf(title, text)
            .asSequence()
            .filter { it.isNotBlank() }
            .joinToString(" - ")

        if (combined.isNotBlank()) {
            CaptureRepository.add(
                CapturedItem(
                    source = sbn.packageName ?: "unknown",
                    text = combined,
                ),
            )
        }
    }
}

package com.example.scampoc.util

import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

object TreeParser {
    fun extractText(node: AccessibilityNodeInfo?): String {
        if (node == null) return ""
        val sb = StringBuilder()
        traverse(node, sb)
        val result = sb.toString()
        Log.d("TreeParser", "Captured: $result")
        return result
    }

    private fun traverse(node: AccessibilityNodeInfo?, sb: StringBuilder) {
        if (node == null) return

        val nodeText = node.text?.toString()
        val nodeDesc = node.contentDescription?.toString()

        val toAppend = when {
            !nodeText.isNullOrBlank() -> nodeText
            !nodeDesc.isNullOrBlank() -> "[$nodeDesc]"
            else -> null
        }

        toAppend?.let {
            if (sb.isNotEmpty()) sb.append(" | ")
            sb.append(it)
        }

        for (i in 0 until node.childCount) {
            val child = try {
                node.getChild(i)
            } catch (_: Exception) {
                null
            }
            traverse(child, sb)
        }
    }
}

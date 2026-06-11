package com.example.scampoc

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scampoc.data.CaptureRepository
import com.example.scampoc.ui.LogAdapter
import android.util.Log
import java.util.Timer
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: LogAdapter
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = LogAdapter()
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<Button>(R.id.btnAccessibility).setOnClickListener {
            Log.d("MainActivity", "Opening Accessibility Settings")
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        findViewById<Button>(R.id.btnNotifications).setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        startPolling()
    }

    private fun startPolling() {
        timer = fixedRateTimer(period = 1000) {
            runOnUiThread {
                val currentItems = CaptureRepository.items.toList()
                val isAccessEnabled = isAccessibilityServiceEnabled()
                
                // Update button text to show status
                findViewById<Button>(R.id.btnAccessibility).text = 
                    if (isAccessEnabled) "Accessibility: ON" else "Enable Accessibility (OFF)"
                
                adapter.submitList(currentItems)
            }
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val expectedService = "$packageName/com.example.scampoc.service.ScreenReadAccessibilityService"
        val enabledServices = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        return enabledServices?.contains(expectedService) == true
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
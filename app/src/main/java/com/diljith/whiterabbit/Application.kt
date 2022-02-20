package com.diljith.whiterabbit

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        ) //MODE_NIGHT_NO – Disables night mode manually.
    }
}
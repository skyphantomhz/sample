package com.example.myapplication

import android.app.Application
import android.content.res.Configuration
import android.view.WindowManager

class MainApplication: Application() {

    override fun onCreate() {
        adjustFontScale(resources.configuration)
        super.onCreate()
    }

    fun adjustFontScale(configuration: Configuration) {
        if (configuration.fontScale > 1.5) {
            configuration.fontScale = 1.5f
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            baseContext.resources.updateConfiguration(configuration, metrics)
        }
    }
}
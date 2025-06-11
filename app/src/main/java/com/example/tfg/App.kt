package com.example.tfg

import android.app.Application
import android.content.Context

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private var _context: Context? = null
        var context: Context
            get() = _context ?: throw IllegalStateException("❌ App.context aún no ha sido inicializado.")

            private set(value) {
                _context = value
            }
    }
}

package dev.sobhy.bmproject

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.initContext(this)
    }
}
package dev.sobhy.bmproject

import android.content.Context

object AppContext {
    lateinit var appContext: Context

    fun initContext(context: Context){
        appContext = context
    }
}
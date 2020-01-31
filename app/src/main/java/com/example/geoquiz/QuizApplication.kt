package com.example.geoquiz

import android.app.Application
import android.util.Log

private const val LOG_TAG = "448.QuizApplication"

class QuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate() called")
    }
}
package com.muazhassan.splitwise.main

import android.app.Application
import timber.log.Timber

class SplitWise : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Application Started")
    }
}
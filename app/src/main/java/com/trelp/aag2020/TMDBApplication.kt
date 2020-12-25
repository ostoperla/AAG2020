package com.trelp.aag2020

import android.app.Application
import timber.log.Timber

class TMDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        internal lateinit var INSTANCE: Application
            private set
    }
}
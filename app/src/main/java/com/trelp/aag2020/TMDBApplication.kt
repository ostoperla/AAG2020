package com.trelp.aag2020

import android.app.Application
import com.trelp.aag2020.di.ComponentOwner
import com.trelp.aag2020.di.Injector
import com.trelp.aag2020.di.application.AppComponent
import com.trelp.aag2020.di.application.DaggerAppComponent
import timber.log.Timber

class TMDBApplication : Application(), ComponentOwner<AppComponent> {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initDagger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDagger() {
        Injector.init(this)
    }

    override fun createComponent() = DaggerAppComponent.factory().create(this)
}
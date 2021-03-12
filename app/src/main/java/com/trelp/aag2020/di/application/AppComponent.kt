package com.trelp.aag2020.di.application

import android.content.Context
import com.trelp.aag2020.di.AppContext
import com.trelp.aag2020.di.ImComponent
import com.trelp.aag2020.di.activity.ActivityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent: ImComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @AppContext context: Context): AppComponent
    }

    fun activityComponentFactory(): ActivityComponent.Factory
}
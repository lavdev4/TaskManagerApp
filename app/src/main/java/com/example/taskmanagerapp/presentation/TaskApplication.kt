package com.example.taskmanagerapp.presentation

import android.app.Application
import com.example.taskmanagerapp.di.ApplicationComponent
import com.example.taskmanagerapp.di.DaggerApplicationComponent
import java.time.Clock
import java.util.Locale

class TaskApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        applicationComponent = DaggerApplicationComponent.builder()
            .context(applicationContext)
            .applicationDataStore(APP_DATA_STORE_NAME)
            .systemClock(Clock.systemDefaultZone())
            .locale(Locale.getDefault())
            .build()
        super.onCreate()
    }

    companion object {
        private const val APP_DATA_STORE_NAME = "application_data_store"
    }
}
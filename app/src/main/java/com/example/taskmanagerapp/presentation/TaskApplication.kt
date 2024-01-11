package com.example.taskmanagerapp.presentation

import android.app.Application
import com.example.taskmanagerapp.di.ApplicationComponent
import com.example.taskmanagerapp.di.DaggerApplicationComponent
import java.time.ZoneId

class TaskApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        applicationComponent = DaggerApplicationComponent.builder()
            .context(applicationContext)
            .applicationDataStore(APP_DATA_STORE_NAME)
            .timeZone(ZoneId.systemDefault())
            .build()
        super.onCreate()
    }

    companion object {
        private const val APP_DATA_STORE_NAME = "application_data_store"
    }
}
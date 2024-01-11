package com.example.taskmanagerapp.di

import android.content.Context
import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.di.annotations.ApplicationDataStore
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.di.modules.DataModule
import com.example.taskmanagerapp.di.modules.DatabaseModule
import com.example.taskmanagerapp.di.modules.ExternalSourceModule
import com.example.taskmanagerapp.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
    DataModule::class,
    DatabaseModule::class,
    ExternalSourceModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface ApplicationComponentBuilder {

        @BindsInstance
        fun context(context: Context) : ApplicationComponentBuilder

        @BindsInstance
        fun applicationDataStore(@ApplicationDataStore dataStoreName: String)

        fun build(): ApplicationComponent
    }
}
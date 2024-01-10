package com.example.taskmanagerapp.di

import android.content.Context
import com.example.taskmanagerapp.di.annotations.ApplicationDataStore
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [

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
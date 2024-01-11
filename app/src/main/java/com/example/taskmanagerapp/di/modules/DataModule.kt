package com.example.taskmanagerapp.di.modules

import com.example.taskmanagerapp.data.repositories.TasksCacheRepositoryImpl
import com.example.taskmanagerapp.data.repositories.TasksExternalRepositoryImpl
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindTasksExternalRepository(impl: TasksExternalRepositoryImpl): TasksExternalRepository

    @Binds
    fun bindTasksCacheRepository(impl: TasksCacheRepositoryImpl): TasksCacheRepository
}
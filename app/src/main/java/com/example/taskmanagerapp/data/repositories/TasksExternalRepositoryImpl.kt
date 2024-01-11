package com.example.taskmanagerapp.data.repositories

import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ApplicationScope
class TasksExternalRepositoryImpl @Inject constructor(
    private val externalTasksSource: ExternalTasksSource
) : TasksExternalRepository {

    override suspend fun add(tasks: List<TaskEntity>) {
        TODO("Not yet implemented")
    }

    override fun get(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }
}
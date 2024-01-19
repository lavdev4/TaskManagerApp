package com.example.taskmanagerapp.data.repositories

import android.util.Log
import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.data.mappers.TaskExternalMapper
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationScope
class TasksExternalRepositoryImpl @Inject constructor(
    private val externalTasksSource: ExternalTasksSource,
    private val mapper: TaskExternalMapper
) : TasksExternalRepository {
    private val externalDispatcher = Dispatchers.IO

    override suspend fun add(tasks: List<TaskEntity>) {
        withContext(externalDispatcher) {
            val mapped = tasks.map(mapper::taskEntityToExternalModel)
            externalTasksSource.add(mapped)
        }
    }

    override fun get(): Flow<List<TaskEntity>> {
        return externalTasksSource.get()
            .map { taskList -> taskList.map(mapper::taskExternalModelToEntity) }
    }
}
package com.example.taskmanagerapp.data.repositories

import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.data.external.TaskExternalModel
import com.example.taskmanagerapp.data.mappers.TaskExternalMapper
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationScope
class TasksExternalRepositoryImpl @Inject constructor(
    private val externalTasksSource: ExternalTasksSource,
    private val mapper: TaskExternalMapper
) : TasksExternalRepository {
    private val externalDispatcher = Dispatchers.IO

    override suspend fun add(task: TaskEntity) {
        withContext(externalDispatcher) {
            val tasks = listOf(task)
            val mapped = tasks.map(mapper::taskEntityToExternalModel)
            externalTasksSource.add(mapped)
        }
    }

    override suspend fun updateTask(task: TaskEntity) {
        add(task)
    }

    override suspend fun removeTask(taskId: String): Boolean {
        return withContext(externalDispatcher) {
            val tasksList = externalTasksSource.getList().toMutableList()
            val removed = tasksList.removeIf { it.id == taskId }
            if (removed) externalTasksSource.refresh(tasksList)
            removed
        }
    }

    override fun getFlow(): Flow<List<TaskEntity>> {
        return externalTasksSource.getFlow()
            .map { taskList -> taskList.map(mapper::taskExternalModelToEntity) }
    }

    override suspend fun getSingle(taskId: String): TaskEntity? {
        return withContext(externalDispatcher) {
            val result = externalTasksSource.getList().find { it.id == taskId }
            return@withContext result?.let { mapper.taskExternalModelToEntity(it) }
        }
    }
}
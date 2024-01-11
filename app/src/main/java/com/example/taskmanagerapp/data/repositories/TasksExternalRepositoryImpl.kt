package com.example.taskmanagerapp.data.repositories

import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.data.mappers.TaskExternalMapper
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ApplicationScope
class TasksExternalRepositoryImpl @Inject constructor(
    private val externalTasksSource: ExternalTasksSource,
    private val mapper: TaskExternalMapper
) : TasksExternalRepository {

    override suspend fun add(tasks: List<TaskEntity>) {
        val mapped = tasks.map(mapper::taskEntityToExternalModel)
        externalTasksSource.add(mapped)
    }

    override fun get(): Flow<List<TaskEntity>> {
        return externalTasksSource.get()
            .map { taskList -> taskList.map(mapper::taskExternalModelToEntity) }
    }
}
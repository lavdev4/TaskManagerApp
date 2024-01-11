package com.example.taskmanagerapp.data.repositories

import com.example.taskmanagerapp.data.cache.database.TasksDao
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ApplicationScope
class TasksCacheRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao
) : TasksCacheRepository {

    override suspend fun add(tasks: List<TaskEntity>) {
        TODO("Not yet implemented")
    }

    override fun get(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingle(taskId: Int): TaskEntity {
        TODO("Not yet implemented")
    }
}
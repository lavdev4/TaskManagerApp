package com.example.taskmanagerapp.domain.repositories

import com.example.taskmanagerapp.domain.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksCacheRepository {
    suspend fun add(tasks: List<TaskEntity>)
    fun get(): Flow<List<TaskEntity>>
    suspend fun getSingle(taskId: Int): TaskEntity
}
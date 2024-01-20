package com.example.taskmanagerapp.domain.repositories

import com.example.taskmanagerapp.domain.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksExternalRepository {
    suspend fun add(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun removeTask(taskId: Int): Boolean
    fun getFlow(): Flow<List<TaskEntity>>
    suspend fun getSingle(taskId: Int): TaskEntity?
}
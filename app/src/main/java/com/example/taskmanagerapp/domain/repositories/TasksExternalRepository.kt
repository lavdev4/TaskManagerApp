package com.example.taskmanagerapp.domain.repositories

import com.example.taskmanagerapp.domain.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksExternalRepository {
    suspend fun add(tasks: List<TaskEntity>)
    fun get(): Flow<List<TaskEntity>>
}
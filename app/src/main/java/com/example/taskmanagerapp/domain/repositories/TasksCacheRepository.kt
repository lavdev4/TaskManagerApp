package com.example.taskmanagerapp.domain.repositories

import com.example.taskmanagerapp.domain.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TasksCacheRepository {
    suspend fun add(tasks: List<TaskEntity>)
    fun get(dateTime: LocalDateTime): Flow<List<TaskEntity>>
    suspend fun getSingle(taskId: Int): TaskEntity
    suspend fun clear()
}
package com.example.taskmanagerapp.domain.repositories

import com.example.taskmanagerapp.domain.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TasksCacheRepository {
    suspend fun update(tasks: List<TaskEntity>)
    fun getFlowByDate(dateTime: LocalDate): Flow<List<TaskEntity>>
    suspend fun getSingle(taskId: Int): TaskEntity
    suspend fun clear()
}
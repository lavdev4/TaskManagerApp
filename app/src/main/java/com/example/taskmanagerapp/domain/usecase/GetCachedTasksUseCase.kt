package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetCachedTasksUseCase @Inject constructor(
    private val tasksCacheRepository: TasksCacheRepository
) {

    fun getTasksFlow(dateTime: LocalDate): Flow<List<TaskEntity>> {
        return tasksCacheRepository.getFlowByDate(dateTime)
    }

    suspend fun getTask(taskId: Int): TaskEntity {
        return tasksCacheRepository.getSingle(taskId)
    }
}
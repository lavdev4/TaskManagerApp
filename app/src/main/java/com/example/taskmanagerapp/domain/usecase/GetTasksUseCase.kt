package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val tasksCacheRepository: TasksCacheRepository
) {
    operator fun invoke(dateTime: LocalDateTime): Flow<List<TaskEntity>> {
        return tasksCacheRepository.get(dateTime)
    }

    suspend fun getTaskData(taskId: Int): TaskEntity {
        return tasksCacheRepository.getSingle(taskId)
    }
}
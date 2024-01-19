package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val tasksCacheRepository: TasksCacheRepository
) {
    operator fun invoke(dateTime: LocalDate): Flow<List<TaskEntity>> {
        return tasksCacheRepository.get(dateTime)
    }

    suspend fun getTaskData(taskId: Int): TaskEntity {
        return tasksCacheRepository.getSingle(taskId)
    }
}
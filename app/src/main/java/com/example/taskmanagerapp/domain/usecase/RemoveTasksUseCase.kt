package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import javax.inject.Inject

class RemoveTasksUseCase @Inject constructor(
    private val taskExternalRepository: TasksExternalRepository
) {

    suspend fun removeTask(taskId: Int) {
        val removed = taskExternalRepository.removeTask(taskId)
        if (!removed) throw RuntimeException("Ambiguous task with id: $taskId")
    }
}
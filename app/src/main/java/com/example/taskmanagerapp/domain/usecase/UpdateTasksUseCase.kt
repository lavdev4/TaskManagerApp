package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import javax.inject.Inject

class UpdateTasksUseCase @Inject constructor(
    private val tasksExternalRepository: TasksExternalRepository,
    private val tasksCacheRepository: TasksCacheRepository
) {
    suspend operator fun invoke() {
        tasksExternalRepository.get().collect {
            tasksCacheRepository.update(it)
        }
    }
}
package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import javax.inject.Inject

class AddTasksUseCase @Inject constructor(
    private val tasksRepository: TasksExternalRepository
) {
    suspend operator fun invoke(task: TaskEntity) { tasksRepository.add(listOf(task)) }
}
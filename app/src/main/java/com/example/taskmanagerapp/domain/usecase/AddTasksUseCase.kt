package com.example.taskmanagerapp.domain.usecase

import android.util.Log
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import javax.inject.Inject

class AddTasksUseCase @Inject constructor(
    private val tasksRepository: TasksExternalRepository
) {
    suspend fun addTask(task: TaskEntity) { tasksRepository.add(task) }
}
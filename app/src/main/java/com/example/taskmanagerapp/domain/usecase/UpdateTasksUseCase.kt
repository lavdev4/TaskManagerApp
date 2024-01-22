package com.example.taskmanagerapp.domain.usecase

import android.util.Log
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import javax.inject.Inject

class UpdateTasksUseCase @Inject constructor(
    private val tasksExternalRepository: TasksExternalRepository,
    private val tasksCacheRepository: TasksCacheRepository
) {
    suspend operator fun invoke() {
        tasksExternalRepository.getFlow().collect {
            Log.d("Tasks", "on update: ${it.size}")
            tasksCacheRepository.update(it)
        }
    }

    suspend fun deactivateTask(taskId: String) {
        tasksExternalRepository.getSingle(taskId)?.let { task ->
            updateTask(task.copy(actual = false))
        } ?: throw RuntimeException("Ambiguous task with id: $taskId")
    }

    private suspend fun updateTask(task: TaskEntity) {
        tasksExternalRepository.updateTask(task)
    }
}
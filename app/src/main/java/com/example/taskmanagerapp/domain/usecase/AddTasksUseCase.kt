package com.example.taskmanagerapp.domain.usecase

import com.example.taskmanagerapp.domain.entities.AddResultEntity
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.entities.TaskRawEntity
import com.example.taskmanagerapp.domain.repositories.TasksExternalRepository
import java.util.UUID
import javax.inject.Inject

class AddTasksUseCase @Inject constructor(
    private val tasksRepository: TasksExternalRepository
) {
    suspend fun addTask(rawTask: TaskRawEntity): AddResultEntity {
        if (rawTask.dateFinish?.isBefore(rawTask.dateStart) == true) {
            return AddResultEntity.InvertedDateStartEnd
        }
        if (rawTask.name.isNullOrEmpty()) return AddResultEntity.NoNameSpecified
        if (rawTask.dateStart == null) return AddResultEntity.NoStartDateSpecified
        if (rawTask.description.isNullOrEmpty()) return AddResultEntity.NoDescriptionSpecified
        if (rawTask.actual == null) return AddResultEntity.NoStatusSpecified

        val task = TaskEntity(
            id = UUID.randomUUID().toString(),
            name = rawTask.name,
            dateStart = rawTask.dateStart,
            dateFinish = rawTask.dateFinish,
            description = rawTask.description,
            actual = rawTask.actual
        )
        tasksRepository.add(task)
        return AddResultEntity.Success
    }
}
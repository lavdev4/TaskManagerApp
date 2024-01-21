package com.example.taskmanagerapp.data.mappers

import com.example.taskmanagerapp.data.external.TaskExternalModel
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import javax.inject.Inject

@ApplicationScope
class TaskExternalMapper @Inject constructor(
    private val dateTimeMapper: DateTimeMapper
) {

    fun taskEntityToExternalModel(entity: TaskEntity): TaskExternalModel {
        return TaskExternalModel(
            id = entity.id,
            dateStart = dateTimeMapper.dateTimeToEpochSecond(entity.dateStart),
            dateFinish = entity.dateFinish?.let { dateTimeMapper.dateTimeToEpochSecond(it) },
            name = entity.name,
            description = entity.description,
            actual = entity.actual
        )
    }

    fun taskExternalModelToEntity(externalModel: TaskExternalModel): TaskEntity {
        return TaskEntity(
            id = externalModel.id,
            dateStart = dateTimeMapper.epochSecondToDateTime(externalModel.dateStart),
            dateFinish = externalModel.dateFinish?.let { dateTimeMapper.epochSecondToDateTime(it) },
            name = externalModel.name,
            description = externalModel.description,
            actual = externalModel.actual
        )
    }
}
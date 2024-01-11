package com.example.taskmanagerapp.data.mappers

import com.example.taskmanagerapp.data.external.TaskExternalModel
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.TaskEntity
import java.time.ZoneId
import javax.inject.Inject

@ApplicationScope
class TaskExternalMapper @Inject constructor(
    private val dateTimeMapper: DateTimeMapper
) {

    fun taskEntityToExternalModel(entity: TaskEntity): TaskExternalModel {
        return TaskExternalModel(
            id = entity.id,
            dateStart = dateTimeMapper.dateTimeToEpochSecond(entity.dateStart),
            dateFinish = dateTimeMapper.dateTimeToEpochSecond(entity.dateFinish),
            name = entity.name,
            description = entity.description
        )
    }

    fun taskExternalModelToEntity(externalModel: TaskExternalModel): TaskEntity {
        return TaskEntity(
            id = externalModel.id,
            dateStart = dateTimeMapper.epochSecondToDateTime(externalModel.dateStart),
            dateFinish = dateTimeMapper.epochSecondToDateTime(externalModel.dateFinish),
            name = externalModel.name,
            description = externalModel.description
        )
    }
}
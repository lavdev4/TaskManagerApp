package com.example.taskmanagerapp.data.mappers

import com.example.taskmanagerapp.data.cache.database.TaskDbModel
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import javax.inject.Inject

@ApplicationScope
class TaskCacheMapper @Inject constructor(
    private val dateTimeMapper: DateTimeMapper
) {

    fun taskEntityToDbModel(entity: TaskEntity): TaskDbModel {
        return TaskDbModel(
            id = entity.id,
            dateStart = dateTimeMapper.dateTimeToEpochSecond(entity.dateStart),
            dateFinish = dateTimeMapper.dateTimeToEpochSecond(entity.dateFinish),
            name = entity.name,
            description = entity.description
        )
    }

    fun taskDbModelToEntity(dbModel: TaskDbModel): TaskEntity {
        return TaskEntity(
            id = dbModel.id,
            dateStart = dateTimeMapper.epochSecondToDateTime(dbModel.dateStart),
            dateFinish = dateTimeMapper.epochSecondToDateTime(dbModel.dateFinish),
            name = dbModel.name,
            description = dbModel.description
        )
    }
}
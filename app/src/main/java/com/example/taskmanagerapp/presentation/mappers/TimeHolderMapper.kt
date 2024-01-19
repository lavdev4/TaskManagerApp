package com.example.taskmanagerapp.presentation.mappers

import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.presentation.adapters.models.TaskData
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import org.w3c.dom.Entity
import java.util.Locale
import javax.inject.Inject

class TimeHolderMapper @Inject constructor(
    private val locale: Locale
) {

    fun mapTaskEntityToTimeHolder(entity: TaskEntity): TimeHolder {
        return TaskData(
            time = entity.dateStart.toLocalTime(),
            id = entity.id,
            name = entity.name,
            locale = locale
        )
    }
}
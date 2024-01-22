package com.example.taskmanagerapp.presentation.mappers

import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.presentation.adapters.models.DisabledTaskDataHolder
import com.example.taskmanagerapp.presentation.adapters.models.TaskDataHolder
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import java.time.Clock
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class TimeHolderMapper @Inject constructor(locale: Locale, clock: Clock) {
    private val dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(locale)
        .withZone(clock.zone)

    fun mapTaskEntityToTimeHolder(entity: TaskEntity): TimeHolder {
        return if (entity.actual) {
            TaskDataHolder(
                time = entity.dateStart.toLocalTime(),
                id = entity.id,
                name = entity.name,
                timeFormatter = dateTimeFormatter,
                description = entity.description
            )
        } else {
            DisabledTaskDataHolder(
                time = entity.dateStart.toLocalTime(),
                id = entity.id,
                name = entity.name,
                timeFormatter = dateTimeFormatter,
                description = entity.description
            )
        }
    }
}
package com.example.taskmanagerapp.presentation.mappers

import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.presentation.adapters.models.DisabledTaskData
import com.example.taskmanagerapp.presentation.adapters.models.TaskData
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class TimeHolderMapper @Inject constructor(locale: Locale) {
    private val dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(locale)

    fun mapTaskEntityToTimeHolder(entity: TaskEntity): TimeHolder {
        return if (entity.actual) {
            TaskData(
                time = dateToTimeString(entity.dateStart),
                id = entity.id,
                name = entity.name
            )
        } else {
            DisabledTaskData(
                time = dateToTimeString(entity.dateStart),
                id = entity.id,
                name = entity.name
            )
        }
    }

    private fun dateToTimeString(date: LocalDateTime): String {
        return date.toLocalTime().format(dateTimeFormatter)
    }
}
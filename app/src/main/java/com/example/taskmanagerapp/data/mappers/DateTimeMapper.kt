package com.example.taskmanagerapp.data.mappers

import com.example.taskmanagerapp.di.annotations.ApplicationScope
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@ApplicationScope
class DateTimeMapper @Inject constructor(
    private val timeZoneId: ZoneId
) {

    fun dateTimeToEpochSecond(dateTime: LocalDateTime): Long {
        return dateTime.atZone(timeZoneId).toEpochSecond()
    }

    fun epochSecondToDateTime(epochSecond: Long): LocalDateTime {
        val instant = Instant.ofEpochSecond(epochSecond)
        return LocalDateTime.ofInstant(instant,timeZoneId)
    }
}
package com.example.taskmanagerapp.data.mappers

import android.util.Log
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.Temporal
import java.time.temporal.TemporalAmount
import javax.inject.Inject

@ApplicationScope
class DateTimeMapper @Inject constructor(
    private val clock: Clock
) {
    private val timeZoneId = clock.zone

    fun dateTimeToEpochSecond(dateTime: LocalDateTime): Long {
        return dateTime.atZone(timeZoneId).toEpochSecond()
    }

    fun epochSecondToDateTime(epochSecond: Long): LocalDateTime {
        val instant = Instant.ofEpochSecond(epochSecond)
        return LocalDateTime.ofInstant(instant, timeZoneId)
    }

    fun dateToEpochSecondInterval(date: LocalDate): DayEpochInterval {
        val dayStart = date.atStartOfDay().atZone(timeZoneId).toEpochSecond()
        val dayEnd = date.atTime(LocalTime.MAX).atZone(timeZoneId).toEpochSecond()
        return DayEpochInterval(dayStart, dayEnd)
    }

    data class DayEpochInterval(
        val dayStart: Long,
        val dayEnd: Long
    )
}
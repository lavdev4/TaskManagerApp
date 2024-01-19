package com.example.taskmanagerapp.presentation.mappers

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class DateMapper @Inject constructor(
    private val clock: Clock
) {

    fun calendarResultDateToLocalDate(year: Int, month: Int, day: Int): LocalDate {
        return LocalDate.of(year, month + 1, day)
    }

    fun epochMillisToLocalDate(epochMillis: Long): LocalDate {
        val instant = Instant.ofEpochMilli(epochMillis)
        return LocalDateTime.ofInstant(instant, clock.zone).toLocalDate()
    }

    fun localDateToEpochMillis(date: LocalDate): Long {
        return date.atStartOfDay(clock.zone).toEpochSecond() * 1000
    }
}
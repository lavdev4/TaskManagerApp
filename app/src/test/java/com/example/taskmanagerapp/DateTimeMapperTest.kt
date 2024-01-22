package com.example.taskmanagerapp

import com.example.taskmanagerapp.data.mappers.DateTimeMapper
import org.junit.Assert
import org.junit.Test
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

class DateTimeMapperTest {
    private val dateTimeMapper = DateTimeMapper(Clock.systemUTC())

    companion object {
        const val INPUT_EPOCH_SECOND = 1705941435L
        val EXPECTED_EPOCH_CONVERT = LocalDateTime.of(2024, 1, 22, 16, 37, 15)

        val INPUT_DATE_TIME = LocalDateTime.of(2024, 1, 22, 16, 37, 15)
        const val EXPECTED_DATE_TIME_CONVERT = 1705941435L

        val INPUT_DATE = LocalDate.of(2024, 1, 22)
        val EXPECTED_DAY_INTERVAL = DateTimeMapper.DayEpochInterval(1705881600L, 1705967999L)
    }

    @Test
    fun epochToDateTimeConvertTest() {
        val convertResult = dateTimeMapper.epochSecondToDateTime(INPUT_EPOCH_SECOND)
        /** LocalDateTime implements equals(). */
        Assert.assertEquals(EXPECTED_EPOCH_CONVERT, convertResult)
    }

    @Test
    fun dateTimeToEpochConvertTest() {
        val convertResult = dateTimeMapper.dateTimeToEpochSecond(INPUT_DATE_TIME)
        Assert.assertEquals(EXPECTED_DATE_TIME_CONVERT, convertResult)
    }

    @Test
    fun dateToEpochIntervalConvertTest() {
        val convertResult = dateTimeMapper.dateToEpochSecondInterval(INPUT_DATE)
        Assert.assertEquals(EXPECTED_DAY_INTERVAL, convertResult)
    }
}
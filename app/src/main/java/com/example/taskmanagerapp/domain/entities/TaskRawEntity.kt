package com.example.taskmanagerapp.domain.entities

import java.time.LocalDateTime

data class TaskRawEntity(
    val name: String?,
    val dateStart: LocalDateTime?,
    val dateFinish: LocalDateTime?,
    val description: String?,
    val actual: Boolean?
)
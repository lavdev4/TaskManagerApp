package com.example.taskmanagerapp.domain.entities

import java.time.LocalDateTime

data class TaskEntity(
    val id: String,
    val dateStart: LocalDateTime,
    val dateFinish: LocalDateTime? = null,
    val name: String,
    val description: String,
    val actual: Boolean
)
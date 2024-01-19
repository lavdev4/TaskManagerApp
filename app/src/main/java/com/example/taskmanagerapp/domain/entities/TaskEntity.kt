package com.example.taskmanagerapp.domain.entities

import java.time.LocalDateTime

data class TaskEntity(
    val id: Int,
    val dateStart: LocalDateTime,
    val dateFinish: LocalDateTime,
    val name: String,
    val description: String
)
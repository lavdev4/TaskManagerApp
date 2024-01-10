package com.example.taskmanagerapp.data.cache.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDbModel(
    @PrimaryKey
    val id: Int,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
)

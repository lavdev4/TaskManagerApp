package com.example.taskmanagerapp.data.cache.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDbModel(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "date_start")
    val dateStart: Long,
    @ColumnInfo(name = "date_finish")
    val dateFinish: Long? = null,
    val name: String,
    val description: String,
    val actual: Boolean
)

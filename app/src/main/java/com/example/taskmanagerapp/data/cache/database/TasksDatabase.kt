package com.example.taskmanagerapp.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskDbModel::class], version = 1, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "tasks_database"
    }

    abstract fun tasksDao(): TasksDao
}
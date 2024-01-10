package com.example.taskmanagerapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.taskmanagerapp.data.cache.database.TasksDao
import com.example.taskmanagerapp.data.cache.database.TasksDatabase
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
interface DatabaseModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideTasksDatabase(context: Context): TasksDatabase {
            return Room.databaseBuilder(context, TasksDatabase::class.java, TasksDatabase.DB_NAME)
                .build()
        }

        @ApplicationScope
        @Provides
        fun provideTasksDao(database: TasksDatabase): TasksDao {
            return database.tasksDao()
        }
    }
}
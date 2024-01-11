package com.example.taskmanagerapp.data.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<TaskDbModel>)

    @Query("SELECT * FROM tasks WHERE date_start = :epochSeconds")
    fun getAllByDate(epochSeconds: Long): Flow<List<TaskDbModel>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getDistinct(id: Int): TaskDbModel

    @Query("DELETE FROM tasks")
    fun clear()
}
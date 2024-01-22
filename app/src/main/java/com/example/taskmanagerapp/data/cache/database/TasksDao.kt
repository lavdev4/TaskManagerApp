package com.example.taskmanagerapp.data.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<TaskDbModel>)

    @Query("DELETE FROM tasks")
    suspend fun clear()

    @Transaction
    suspend fun update(data: List<TaskDbModel>) {
        clear()
        insert(data)
    }

    @Query("SELECT * FROM tasks WHERE date_start BETWEEN :epochSecondsStart AND :epochSecondsEnd")
    fun getAllByDate(epochSecondsStart: Long, epochSecondsEnd: Long): Flow<List<TaskDbModel>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getDistinct(id: String): TaskDbModel
}
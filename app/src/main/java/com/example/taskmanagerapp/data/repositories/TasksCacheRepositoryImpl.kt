package com.example.taskmanagerapp.data.repositories

import com.example.taskmanagerapp.data.cache.database.TasksDao
import com.example.taskmanagerapp.data.mappers.TaskCacheMapper
import com.example.taskmanagerapp.data.mappers.DateTimeMapper
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class TasksCacheRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val taskCacheMapper: TaskCacheMapper,
    private val dateTimeMapper: DateTimeMapper
) : TasksCacheRepository {

    override suspend fun add(tasks: List<TaskEntity>) {
        val mapped = tasks.map(taskCacheMapper::taskEntityToDbModel)
        tasksDao.insert(mapped)
    }

    override fun get(dateTime: LocalDateTime): Flow<List<TaskEntity>> {
        val secondsEpoch = dateTimeMapper.dateTimeToEpochSecond(dateTime)
        return tasksDao.getAllByDate(secondsEpoch)
            .map { taskList -> taskList.map(taskCacheMapper::taskDbModelToEntity) }
    }

    override suspend fun getSingle(taskId: Int): TaskEntity {
        val task = tasksDao.getDistinct(taskId)
        return taskCacheMapper.taskDbModelToEntity(task)
    }

    override suspend fun clear() {
        tasksDao.clear()
    }
}
package com.example.taskmanagerapp.data.repositories

import com.example.taskmanagerapp.data.cache.database.TasksDao
import com.example.taskmanagerapp.data.mappers.TaskCacheMapper
import com.example.taskmanagerapp.data.mappers.DateTimeMapper
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.repositories.TasksCacheRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@ApplicationScope
class TasksCacheRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val taskCacheMapper: TaskCacheMapper,
    private val dateTimeMapper: DateTimeMapper
) : TasksCacheRepository {
    private val databaseDispatcher = Dispatchers.IO

    override suspend fun update(tasks: List<TaskEntity>) {
        withContext(databaseDispatcher) {
            val mapped = tasks.map(taskCacheMapper::taskEntityToDbModel)
            tasksDao.update(mapped)
        }
    }

    override fun getFlowByDate(dateTime: LocalDate): Flow<List<TaskEntity>> {
        val interval = dateTimeMapper.dateToEpochSecondInterval(dateTime)
        return tasksDao.getAllByDate(interval.dayStart, interval.dayEnd)
            .map { taskList -> taskList.map(taskCacheMapper::taskDbModelToEntity) }
    }

    override suspend fun getSingle(taskId: String): TaskEntity {
        return withContext(databaseDispatcher) {
            val task = tasksDao.getDistinct(taskId)
            taskCacheMapper.taskDbModelToEntity(task)
        }
    }

    override suspend fun clear() {
        withContext(databaseDispatcher) { tasksDao.clear() }
    }
}
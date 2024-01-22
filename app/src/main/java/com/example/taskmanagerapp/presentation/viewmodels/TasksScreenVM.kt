package com.example.taskmanagerapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.usecase.GetCachedTasksUseCase
import com.example.taskmanagerapp.domain.usecase.RemoveTasksUseCase
import com.example.taskmanagerapp.domain.usecase.UpdateTasksUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class TasksScreenVM @Inject constructor(
    private val getCachedTasksUseCase: GetCachedTasksUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase,
    private val removeDataUseCase: RemoveTasksUseCase
) : ViewModel() {
    var currentDate: LocalDate? = null
    private lateinit var tasksFlow: StateFlow<List<TaskEntity>?>

    init { viewModelScope.launch { updateTasksUseCase() } }

    fun deactivateTask(taskId: String) {
        viewModelScope.launch { updateTasksUseCase.deactivateTask(taskId) }
    }

    fun removeTask(taskId: String) {
        viewModelScope.launch { removeDataUseCase.removeTask(taskId) }
    }

    fun getTasks(date: LocalDate): StateFlow<List<TaskEntity>?> {
        if (currentDate != date) {
            updateTasksFlow(date)
            currentDate = date
        }
        return tasksFlow
    }

    private fun updateTasksFlow(selectedDate: LocalDate) {
        tasksFlow = getCachedTasksUseCase.getTasksFlow(selectedDate)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )
    }
}
package com.example.taskmanagerapp.presentation.viewmodels

import android.icu.text.FormattedValue
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.usecase.AddTasksUseCase
import com.example.taskmanagerapp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerapp.domain.usecase.UpdateTasksUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class TasksScreenVM @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase
) : ViewModel() {
    var currentDate: LocalDate? = null
    private lateinit var tasksFlow: StateFlow<List<TaskEntity>?>

    init { viewModelScope.launch { updateTasksUseCase() } }

    fun getTasks(date: LocalDate): StateFlow<List<TaskEntity>?> {
        if (currentDate != date) {
            updateTasksFlow(date)
            currentDate = date
        }
        return tasksFlow
    }

    private fun updateTasksFlow(selectedDate: LocalDate) {
        tasksFlow = getTasksUseCase(selectedDate)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )
    }
}
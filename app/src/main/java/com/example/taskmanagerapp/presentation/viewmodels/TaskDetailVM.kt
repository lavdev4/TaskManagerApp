package com.example.taskmanagerapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.domain.usecase.GetCachedTasksUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskDetailVM @Inject constructor(
    private val getTasksUseCase: GetCachedTasksUseCase
) : ViewModel() {
    private val _taskState = MutableStateFlow<TaskEntity?>(null)
    val taskState: StateFlow<TaskEntity?>
        get() = _taskState

    fun initialize(taskId: Int) {
         viewModelScope.launch {
             val data = getTasksUseCase.getTask(taskId)
             _taskState.value = data
         }
     }
}
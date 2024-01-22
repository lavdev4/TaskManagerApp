package com.example.taskmanagerapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.entities.AddResultEntity
import com.example.taskmanagerapp.domain.entities.TaskRawEntity
import com.example.taskmanagerapp.domain.usecase.AddTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskScreenVM @Inject constructor(
    private val addTasksUseCase: AddTasksUseCase
) : ViewModel() {
    private val _addResultState = MutableStateFlow<AddResultEntity?>(null)
    val addResultState: StateFlow<AddResultEntity?>
        get() = _addResultState

    fun addTask(rawTask: TaskRawEntity) {
        viewModelScope.launch {
            _addResultState.value = addTasksUseCase.addTask(rawTask)
        }
    }
}
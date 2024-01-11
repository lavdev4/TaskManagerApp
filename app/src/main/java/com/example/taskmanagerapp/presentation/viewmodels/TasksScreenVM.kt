package com.example.taskmanagerapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskmanagerapp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerapp.domain.usecase.UpdateTasksUseCase
import javax.inject.Inject

class TasksScreenVM @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase
) : ViewModel() {
}
package com.example.taskmanagerapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskmanagerapp.domain.usecase.AddTasksUseCase
import javax.inject.Inject

class AddTaskScreenVM @Inject constructor(
    private val addTasksUseCase: AddTasksUseCase
) : ViewModel() {
}
package com.example.taskmanagerapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskmanagerapp.domain.usecase.GetCachedTasksUseCase
import javax.inject.Inject

class TaskDetailVM @Inject constructor(
    private val getTasksUseCase: GetCachedTasksUseCase
) : ViewModel() {
}
package com.example.taskmanagerapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.taskmanagerapp.di.annotations.ViewModelKey
import com.example.taskmanagerapp.presentation.screens.AddTaskScreen
import com.example.taskmanagerapp.presentation.viewmodels.AddTaskScreenVM
import com.example.taskmanagerapp.presentation.viewmodels.TaskDetailVM
import com.example.taskmanagerapp.presentation.viewmodels.TasksScreenVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(TasksScreenVM::class)
    @Binds
    fun bindTasksScreenViewModel(impl: TasksScreenVM): ViewModel

    @IntoMap
    @ViewModelKey(TaskDetailVM::class)
    @Binds
    fun bindTaskDetailViewModel(impl: TaskDetailVM): ViewModel

    @IntoMap
    @ViewModelKey(AddTaskScreenVM::class)
    @Binds
    fun bindAddTaskScreenViewModel(impl: AddTaskScreenVM): ViewModel
}
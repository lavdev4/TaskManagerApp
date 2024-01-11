package com.example.taskmanagerapp.di

import com.example.taskmanagerapp.di.annotations.TaskActivityScope
import com.example.taskmanagerapp.presentation.screens.AddTaskScreen
import com.example.taskmanagerapp.presentation.screens.TaskDetailScreen
import com.example.taskmanagerapp.presentation.screens.TasksScreen
import dagger.Subcomponent

@TaskActivityScope
@Subcomponent
interface TaskActivitySubcomponent {

    fun inject(impl: TasksScreen)

    fun inject(impl: TaskDetailScreen)

    fun inject(impl: AddTaskScreen)

    @Subcomponent.Builder
    interface TaskActivitySubcomponentBuilder {
        fun build(): TaskActivitySubcomponent
    }
}
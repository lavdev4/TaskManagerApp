package com.example.taskmanagerapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.databinding.ActivityTaskBinding
import com.example.taskmanagerapp.di.TaskActivitySubcomponent

class TaskActivity : AppCompatActivity() {
    lateinit var activitySubcomponent: TaskActivitySubcomponent
    private lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        activitySubcomponent =
            (application as TaskApplication).applicationComponent
            .taskActivitySubcomponent()
            .build()
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
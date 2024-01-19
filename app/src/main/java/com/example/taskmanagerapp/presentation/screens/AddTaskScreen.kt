package com.example.taskmanagerapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.taskmanagerapp.databinding.FragmentAddTaskBinding
import com.example.taskmanagerapp.presentation.TaskActivity
import com.example.taskmanagerapp.presentation.viewmodels.AddTaskScreenVM
import com.example.taskmanagerapp.presentation.viewmodels.factories.ViewModelFactory
import javax.inject.Inject

class AddTaskScreen : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<AddTaskScreenVM> { viewModelFactory }
    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentAddTaskBinding is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as TaskActivity).activitySubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
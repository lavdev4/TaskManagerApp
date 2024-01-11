package com.example.taskmanagerapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.taskmanagerapp.databinding.FragmentTaskDetailBinding
import com.example.taskmanagerapp.databinding.FragmentTasksBinding
import com.example.taskmanagerapp.presentation.TaskActivity
import com.example.taskmanagerapp.presentation.viewmodels.AppViewModelFactory
import com.example.taskmanagerapp.presentation.viewmodels.TaskDetailVM
import com.example.taskmanagerapp.presentation.viewmodels.TasksScreenVM
import javax.inject.Inject

class TasksScreen : Fragment() {
    @Inject lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by viewModels<TasksScreenVM> { viewModelFactory }
    private lateinit var navController: NavController
    private var _binding: FragmentTasksBinding? = null
    private val binding: FragmentTasksBinding
        get() = _binding ?: throw RuntimeException("FragmentTasksBinding is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as TaskActivity).activitySubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
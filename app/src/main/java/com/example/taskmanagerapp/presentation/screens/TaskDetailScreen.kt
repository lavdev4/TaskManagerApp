package com.example.taskmanagerapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.taskmanagerapp.databinding.FragmentTaskDetailBinding
import com.example.taskmanagerapp.presentation.TaskActivity
import com.example.taskmanagerapp.presentation.viewmodels.factories.ViewModelFactory
import com.example.taskmanagerapp.presentation.viewmodels.TaskDetailVM
import javax.inject.Inject

class TaskDetailScreen : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<TaskDetailVM> { viewModelFactory }
    private val args: TaskDetailScreenArgs by navArgs()
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding: FragmentTaskDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskDetailBinding is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as TaskActivity).activitySubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
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
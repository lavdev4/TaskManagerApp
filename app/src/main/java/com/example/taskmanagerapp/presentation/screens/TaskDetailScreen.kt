package com.example.taskmanagerapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.databinding.FragmentTaskDetailBinding
import com.example.taskmanagerapp.domain.entities.TaskEntity
import com.example.taskmanagerapp.presentation.TaskActivity
import com.example.taskmanagerapp.presentation.viewmodels.TaskDetailVM
import com.example.taskmanagerapp.presentation.viewmodels.factories.ViewModelFactory
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class TaskDetailScreen : Fragment() {
    @Inject lateinit var clock: Clock
    @Inject lateinit var locale: Locale
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
        viewModel.initialize(args.taskId)
        getTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTasks() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.taskState
                    .filterNotNull()
                    .collect(::fillTextFields)
            }
        }
    }

    private fun fillTextFields(task: TaskEntity) {
        binding.nameText.text = task.name
        binding.status.text = if (task.actual) {
            getString(R.string.task_status_active)
        } else {
            getString(R.string.task_status_uncative)
        }
        binding.descriptionText.text = task.description
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(locale)
            .withZone(clock.zone)
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(locale)
            .withZone(clock.zone)
        val timeStart = task.dateStart.toLocalTime().format(timeFormatter)
        val timeEnd = task.dateFinish?.toLocalTime()?.format(timeFormatter)
        timeEnd?.let {
            if (timeEnd != timeStart) {
                binding.timeText.text =
                    getString(R.string.time_start_end_pattern, timeStart, timeEnd)
            } else {
                binding.timeText.text = timeEnd
            }
        } ?: run {
            binding.timeText.text = timeStart
        }
        val dateStart = task.dateStart.toLocalDate().format(dateFormatter)
        val dateEnd = task.dateFinish?.toLocalDate()?.format(dateFormatter)
        binding.dateStartText.text = dateStart
        dateEnd?.let { date ->
            if (dateEnd != dateStart) {
                binding.dateEnd.visibility = View.VISIBLE
                binding.dateEndText.text = date
            }
        }
    }
}
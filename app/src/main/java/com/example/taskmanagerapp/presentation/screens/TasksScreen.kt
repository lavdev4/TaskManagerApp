package com.example.taskmanagerapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagerapp.databinding.FragmentTasksBinding
import com.example.taskmanagerapp.presentation.TaskActivity
import com.example.taskmanagerapp.presentation.adapters.TimeHolderAdapter
import com.example.taskmanagerapp.presentation.adapters.decorators.AdaptiveSpacingItemDecorator
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import com.example.taskmanagerapp.presentation.adapters.callbacks.ItemSwipeCallback
import com.example.taskmanagerapp.presentation.mappers.DateMapper
import com.example.taskmanagerapp.presentation.mappers.TimeHolderMapper
import com.example.taskmanagerapp.presentation.viewmodels.factories.ViewModelFactory
import com.example.taskmanagerapp.presentation.viewmodels.TasksScreenVM
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class TasksScreen : Fragment() {
    @Inject lateinit var dateMapper: DateMapper
    @Inject lateinit var timeHolderMapper: TimeHolderMapper
    @Inject lateinit var locale: Locale
    @Inject lateinit var clock: Clock
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<TasksScreenVM> { viewModelFactory }
    private lateinit var navController: NavController
    private var _binding: FragmentTasksBinding? = null
    private val binding: FragmentTasksBinding
        get() = _binding ?: throw RuntimeException("FragmentTasksBinding is null")
    private var appBarOffsetListener: OnOffsetChangedListener? = null

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
        val tasksAdapter = initializeTasksList()
        val currentDate = initializeCalendar(binding.calendar, tasksAdapter)
        observeTasksData(currentDate, tasksAdapter)
        setAppBarOffsetListener()
        setFabClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        removeAppBarOffsetListener()
    }

    private fun initializeCalendar(
        calendarView: CalendarView,
        adapterToDispatch: TimeHolderAdapter,
    ): LocalDate {
        setCalendarDateListener(calendarView, adapterToDispatch)
        return viewModel.currentDate?.let { date ->
            calendarView.date = dateMapper.localDateToEpochMillis(date)
            date
        } ?: run {
            return@run dateMapper.epochMillisToLocalDate(calendarView.date)
        }
    }

    private fun setCalendarDateListener(
        calendarView: CalendarView,
        adapterToDispatch: TimeHolderAdapter
    ) {
        calendarView.setOnDateChangeListener { _, year, month, day ->
            val date = dateMapper.calendarResultDateToLocalDate(year, month, day)
            observeTasksData(date, adapterToDispatch)
        }
    }

    private fun observeTasksData(observedDate: LocalDate, adapterToDispatch: TimeHolderAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getTasks(observedDate)
                    .filterNotNull()
                    .map { dataList -> dataList.map(timeHolderMapper::mapTaskEntityToTimeHolder) }
                    .collect { adapterToDispatch.submitList(it) }
            }
        }
    }

    private fun initializeTasksList(): TimeHolderAdapter {
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(locale)
        val adapter = TimeHolderAdapter(
            dateTimeFormatter,
            ::onTasksItemClick,
            ::onTaskItemSwipeRight,
            ::onTaskItemSwipeLeft
        )
        val touchHelper = ItemTouchHelper(ItemSwipeCallback())
        val decorator = AdaptiveSpacingItemDecorator(0.3f, 6f)
        with(binding.tasksList) {
            layoutManager = LinearLayoutManager(requireContext())
            recycledViewPool.setMaxRecycledViews(TimeHolder.TIME_CATEGORY_VIEW, 24)
            addItemDecoration(decorator)
            touchHelper.attachToRecyclerView(this)
            setAdapter(adapter)
        }
        return adapter
    }

    private fun onTasksItemClick(taskId: Int) {
        navigateToDetailScreen(taskId)
    }

    private fun onTaskItemSwipeRight(taskId: Int) {
        viewModel.deactivateTask(taskId)
    }

    private fun onTaskItemSwipeLeft(taskId: Int) {
        viewModel.removeTask(taskId)
    }

    private fun navigateToDetailScreen(taskId: Int) {
        val direction = TasksScreenDirections.actionTasksScreenToTaskDetailScreen(taskId)
        navController.navigate(direction)
    }

    private fun navigateToAddScreen() {
        val direction = TasksScreenDirections.actionTasksScreenToAddTaskScreen()
        navController.navigate(direction)
    }

    private fun setFabClickListener() {
        binding.fab.setOnClickListener { navigateToAddScreen() }
    }

    private fun setAppBarOffsetListener() {
        val fab = binding.fab
        appBarOffsetListener = OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset + appBarLayout.height == 0) fab.hide() else fab.show()
        }
        binding.appBar.addOnOffsetChangedListener(appBarOffsetListener)
    }

    private fun removeAppBarOffsetListener() {
        binding.appBar.removeOnOffsetChangedListener(appBarOffsetListener)
    }
}
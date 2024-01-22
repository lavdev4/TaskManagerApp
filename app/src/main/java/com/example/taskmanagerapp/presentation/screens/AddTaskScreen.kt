package com.example.taskmanagerapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.databinding.FragmentAddTaskBinding
import com.example.taskmanagerapp.domain.entities.AddResultEntity
import com.example.taskmanagerapp.domain.entities.TaskRawEntity
import com.example.taskmanagerapp.presentation.TaskActivity
import com.example.taskmanagerapp.presentation.mappers.DateMapper
import com.example.taskmanagerapp.presentation.viewmodels.AddTaskScreenVM
import com.example.taskmanagerapp.presentation.viewmodels.factories.ViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class AddTaskScreen : Fragment() {
    @Inject lateinit var locale: Locale
    @Inject lateinit var clock: Clock
    @Inject lateinit var dateMapper: DateMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<AddTaskScreenVM> { viewModelFactory }
    private var navController: NavController? = null
    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentAddTaskBinding is null")
    private lateinit var dateFormatter: DateTimeFormatter
    private lateinit var timeFormatter: DateTimeFormatter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as TaskActivity).activitySubcomponent.inject(this)
        dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(locale)
            .withZone(clock.zone)
        timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(locale)
            .withZone(clock.zone)
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
        navController = Navigation.findNavController(view)
        setupDateClickListener(binding.dateStartText)
        setupDateClickListener(binding.dateEndText)
        setupTimeClickListener(binding.timeStartText, false)
        setupTimeClickListener(binding.timeEndText, true)
        setupAddButtonListener(binding.addButton)
        observeAddResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAddButtonListener(button: Button) {
        button.setOnClickListener { viewModel.addTask(collectTextFieldData()) }
    }

    private fun setupDateClickListener(dateView: TextInputEditText) {
        dateView.keyListener = null
        dateView.setOnClickListener {
            getDatePicker().show(parentFragmentManager, DATE_PICKER_TAG)
        }
    }

    private fun setupTimeClickListener(timeView: TextInputEditText, endTime: Boolean) {
        timeView.keyListener = null
        timeView.setOnClickListener {
            val title = if (!endTime) {
                getString(R.string.time_picker_label_start)
            } else {
                getString(R.string.time_picker_label_end)
            }
            getTimePicker(title, endTime).show(parentFragmentManager, TIME_PICKER_TAG)
        }
    }

    private fun observeAddResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.addResultState
                    .filterNotNull()
                    .collect(::reactToResult)
            }
        }
    }

    private fun reactToResult(addResult: AddResultEntity) {
        when (addResult) {
            AddResultEntity.Success -> navController?.navigateUp()
            AddResultEntity.NoNameSpecified -> showToast(getString(R.string.no_name_toast))
            AddResultEntity.InvertedDateStartEnd -> showToast(getString(R.string.date_inverted_toast))
            AddResultEntity.NoStartDateSpecified -> showToast(getString(R.string.no_start_date_time_toast))
            AddResultEntity.NoDescriptionSpecified -> showToast(getString(R.string.no_description_toast))
            AddResultEntity.NoStatusSpecified -> showToast(getString(R.string.no_status_toast))
        }
    }

    private fun getDatePicker(): MaterialDatePicker<Pair<Long, Long>> {
        val constraintsRange = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()
        return MaterialDatePicker.Builder
            .dateRangePicker()
            .setCalendarConstraints(constraintsRange)
            .setTitleText(getString(R.string.date_picker_label))
            .setPositiveButtonText(getString(R.string.date_picker_positive_button))
            .build()
            .apply { addOnPositiveButtonClickListener(::setDateText) }
    }

    private fun setDateText(dateRange: Pair<Long, Long>) {
        val dateStart = dateMapper.epochMillisToLocalDate(dateRange.first).format(dateFormatter)
        val dateEnd = dateMapper.epochMillisToLocalDate(dateRange.second).format(dateFormatter)
        binding.dateStartText.setText(dateStart)
        binding.dateEndText.setText(dateEnd)
    }

    private fun getTimePicker(titleText: String, endTime: Boolean): MaterialTimePicker {
        return MaterialTimePicker.Builder()
            .setTitleText(titleText)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTimeFormat(if (is24hourFormat()) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    if (!endTime) {
                        setStartTimeText(this.hour, this.minute)
                    } else {
                        setEndTimeText(this.hour, this.minute)
                    }
                }
            }
    }

    private fun setStartTimeText(hour: Int, minute: Int) {
        val time = LocalTime.of(hour, minute).format(timeFormatter)
        binding.timeStartText.setText(time)
    }

    private fun setEndTimeText(hour: Int, minute: Int) {
        val time = LocalTime.of(hour, minute).format(timeFormatter)
        binding.timeEndText.setText(time)
    }

    private fun collectTextFieldData(): TaskRawEntity {
        val name = binding.nameText.text.toString()
        val description = binding.descriptionText.text.toString()
        val startTimeText = binding.timeStartText.text.toString()
        val endTimeText = binding.timeEndText.text.toString()
        val startDateText = binding.dateStartText.text.toString()
        val endDateText = binding.dateEndText.text.toString()
        val startTime = parseTime(startTimeText)
        val endTime = parseTime(endTimeText)
        val startDate = parseDate(startDateText)
        val endDate = parseDate(endDateText)
        return TaskRawEntity(
            name = name.ifEmpty { null },
            dateStart = getDateTime(startDate, startTime),
            dateFinish = getDateTime(endDate, endTime),
            description = description.ifEmpty { null },
            actual = true
        )
    }

    private fun parseDate(date: String): LocalDate? {
        return if (date.isNotEmpty()) LocalDate.from(dateFormatter.parse(date)) else null
    }

    private fun parseTime(time: String): LocalTime? {
        return if (time.isNotEmpty()) LocalTime.from(timeFormatter.parse(time)) else null
    }

    private fun getDateTime(date: LocalDate?, time: LocalTime?): LocalDateTime? {
        return if (date != null && time != null) LocalDateTime.of(date, time) else null
    }

    private fun is24hourFormat(): Boolean {
        return DateFormat.is24HourFormat(requireContext())
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val DATE_PICKER_TAG = "date_picker"
        private const val TIME_PICKER_TAG = "time_picker"
    }
}
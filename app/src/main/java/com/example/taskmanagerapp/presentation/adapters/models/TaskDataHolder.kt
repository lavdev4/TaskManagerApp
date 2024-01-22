package com.example.taskmanagerapp.presentation.adapters.models

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.presentation.adapters.factories.TimeViewHolderFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class TaskDataHolder(
    override val time: LocalTime,
    override val id: String,
    val name: String,
    val description: String,
    val isActivated: Boolean = true,
    private val timeFormatter: DateTimeFormatter
) : TimeHolder(timeFormatter) {
    override val viewType = TASK_DATA_VIEW
    override val sortPriority = false

    override fun bindViewHolder(viewHolder: ViewHolder) {
        val binding = (viewHolder as TimeViewHolderFactory.TaskDataViewHolder).binding
        binding.time.text = formatTime(time)
        binding.name.text = name
        binding.description.text = description
    }

    override fun compareItems(item: TimeHolder): Boolean {
        if (item.viewType != this.viewType) return false
        return (item as TaskDataHolder).id == this.id
    }

    override fun compareContents(item: TimeHolder): Boolean {
        return item == this
    }
}
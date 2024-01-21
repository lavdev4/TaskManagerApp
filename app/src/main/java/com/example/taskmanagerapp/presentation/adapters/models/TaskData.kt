package com.example.taskmanagerapp.presentation.adapters.models

import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.presentation.adapters.factories.TimeViewHolderFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class TaskData(
    override val time: LocalTime,
    override val id: Int,
    val name: String,
    val isActivated: Boolean = true,
    private val timeFormatter: DateTimeFormatter
) : TimeHolder(timeFormatter) {

    override val viewType = TASK_DATA_VIEW

    override fun bindViewHolder(viewHolder: ViewHolder) {
        val binding = (viewHolder as TimeViewHolderFactory.TaskDataViewHolder).binding
        binding.time.text = formatTime(time)
        binding.name.text = name
    }

    override fun compareItems(item: TimeHolder): Boolean {
        if (item.viewType != this.viewType) return false
        return (item as TaskData).id == this.id
    }

    override fun compareContents(item: TimeHolder): Boolean {
        return item == this
    }
}
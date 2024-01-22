package com.example.taskmanagerapp.presentation.adapters.models

import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagerapp.presentation.adapters.factories.TimeViewHolderFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class DisabledTaskDataHolder(
    override val time: LocalTime,
    override val id: String,
    val name: String,
    private val timeFormatter: DateTimeFormatter
) : TimeHolder(timeFormatter) {
    override val viewType: Int = DISABLED_TASK_DATA_VIEW
    override val sortPriority = false

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        val binding = (viewHolder as TimeViewHolderFactory.DisabledTDViewHolder).binding
        binding.time.text = formatTime(time)
        binding.name.text = name
    }

    override fun compareItems(item: TimeHolder): Boolean {
        if (item.viewType != this.viewType) return false
        return (item as DisabledTaskDataHolder).id == this.id
    }

    override fun compareContents(item: TimeHolder): Boolean {
        return item == this
    }
}
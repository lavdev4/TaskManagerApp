package com.example.taskmanagerapp.presentation.adapters.models

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.presentation.adapters.TimeHolderAdapter
import com.example.taskmanagerapp.presentation.adapters.factories.TimeViewHolderFactory
import java.time.LocalTime
import java.util.Locale

data class TaskData(
    override val time: String,
    override val id: Int,
    val name: String
) : TimeHolder() {

    override val viewType = TASK_DATA_VIEW

    override fun bindViewHolder(viewHolder: ViewHolder) {
        val binding = (viewHolder as TimeViewHolderFactory.TaskDataViewHolder).binding
        binding.time.text = time
        binding.name.text = name
    }

    override fun compareItems(item: TimeHolder): Boolean {
        if (item.viewType != this.viewType) return false
        return (item as TaskData).id == this.id
    }

    override fun compareContents(item: TimeHolder): Boolean {
        return with(item as TaskData) {
            this@with.name == this@TaskData.name &&
                    this@with.time == this@TaskData.time
        }
    }
}
package com.example.taskmanagerapp.presentation.adapters.models

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

abstract class TimeHolder {
    abstract val id: Int?
    abstract val time: String
    abstract val viewType: Int

    abstract fun bindViewHolder(viewHolder: ViewHolder)

    abstract fun compareItems(item: TimeHolder): Boolean

    abstract fun compareContents(item: TimeHolder): Boolean

    companion object {
        const val TIME_CATEGORY_VIEW = 1
        const val TASK_DATA_VIEW = 2
        const val DISABLED_TASK_DATA_VIEW = 3
    }
}
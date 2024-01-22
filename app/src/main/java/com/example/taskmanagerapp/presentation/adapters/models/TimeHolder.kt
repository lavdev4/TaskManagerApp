package com.example.taskmanagerapp.presentation.adapters.models

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

abstract class TimeHolder(private val timeFormatter: DateTimeFormatter) {
    abstract val id: String?
    abstract val time: LocalTime
    abstract val viewType: Int
    /** [sortPriority] is necessary to determine which of two identical list elements
     * will have a higher position in the list. */
    abstract val sortPriority: Boolean

    abstract fun bindViewHolder(viewHolder: ViewHolder)

    abstract fun compareItems(item: TimeHolder): Boolean

    abstract fun compareContents(item: TimeHolder): Boolean

    protected fun formatTime(time: LocalTime): String {
        return time.format(timeFormatter)
    }

    companion object {
        const val TIME_CATEGORY_VIEW = 1
        const val TASK_DATA_VIEW = 2
        const val DISABLED_TASK_DATA_VIEW = 3
    }
}
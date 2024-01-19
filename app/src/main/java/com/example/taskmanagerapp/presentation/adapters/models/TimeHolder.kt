package com.example.taskmanagerapp.presentation.adapters.models

import androidx.recyclerview.widget.RecyclerView
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

abstract class TimeHolder(private val locale: Locale) {
    abstract val time: LocalTime
    abstract val viewType: Int

    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder)

    abstract fun compareItems(item: TimeHolder): Boolean

    abstract fun compareContents(item: TimeHolder): Boolean

    protected fun formatTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale)
        return time.format(formatter)
    }

    companion object {
        const val TIME_CATEGORY_VIEW = 0
        const val TASK_DATA_VIEW = 1
    }
}
package com.example.taskmanagerapp.presentation.adapters

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.presentation.adapters.diffutills.TimeHolderDiffUtil
import com.example.taskmanagerapp.presentation.adapters.factories.TimeViewHolderFactory
import com.example.taskmanagerapp.presentation.adapters.models.TaskData
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import java.time.LocalTime
import java.util.Locale

class TimeHolderAdapter(
    locale: Locale
) : ListAdapter<TimeHolder, ViewHolder>(TimeHolderDiffUtil()) {
    private val timeCategoryList = createCategoriesData(locale)
    private var cachedDataList: List<TimeHolder>? = null

    init { super.submitList(timeCategoryList as List<TimeHolder>?) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TimeViewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).bindViewHolder(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun submitList(list: List<TimeHolder>?) {
        initList(list)?.let { super.submitList(it) }
    }

    override fun submitList(list: List<TimeHolder>?, commitCallback: Runnable?) {
        initList(list)?.let { super.submitList(it, commitCallback) }
    }

    private fun initList(dataList: List<TimeHolder>?): MutableList<TimeHolder>? {
        if (dataList == cachedDataList) return null
        return dataList?.let { categorizeList(it) }
    }

    private fun categorizeList(dataList: List<TimeHolder>): MutableList<TimeHolder> {
        return dataList.toMutableList().apply {
            addAll(timeCategoryList)
            sortBy { it.time }
        }
    }

    private fun createCategoriesData(locale: Locale): MutableList<TimeCategory> {
        val categories = mutableListOf<TimeCategory>()
        for (hour in 0..23) {
            val time = LocalTime.of(hour, 0)
            categories.add(TimeCategory(time, locale))
        }
        return categories
    }

    private data class TimeCategory(
        override val time: LocalTime,
        private val locale: Locale
    ) : TimeHolder(locale) {

        override val viewType = TIME_CATEGORY_VIEW

        override fun bindViewHolder(viewHolder: ViewHolder) {
            (viewHolder as TimeViewHolderFactory.TimeCategoryViewHolder).text = formatTime(time)
        }

        override fun compareItems(item: TimeHolder): Boolean {
            if (item.viewType != this.viewType) return false
            return item.time == this.time
        }

        override fun compareContents(item: TimeHolder): Boolean {
            return true
        }
    }
}
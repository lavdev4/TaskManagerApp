package com.example.taskmanagerapp.presentation.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.presentation.adapters.callbacks.ItemClickConsumer
import com.example.taskmanagerapp.presentation.adapters.diffutills.TimeHolderDiffUtil
import com.example.taskmanagerapp.presentation.adapters.factories.TimeViewHolderFactory
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import com.example.taskmanagerapp.presentation.adapters.callbacks.ItemSwipeConsumer
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeHolderAdapter(
    timeFormatter: DateTimeFormatter,
    orientationPortrait: Boolean,
    private val onItemClickCallback: (itemId: String) -> Unit,
    private val onItemDeactivateCallback: (itemId: String) -> Unit,
    private val onItemRemoveCallback: (itemId: String) -> Unit
) : ListAdapter<TimeHolder, ViewHolder>(TimeHolderDiffUtil()), ItemClickConsumer, ItemSwipeConsumer {

    private val timeCategoryList = createCategoriesData(timeFormatter)
    private var cachedDataList: List<TimeHolder>? = null
    private val viewHolderFactory = TimeViewHolderFactory.Builder
        .clickCallback(this)
        .swipeCallback(this)
        .orientationPortrait(orientationPortrait)
        .build()

    init { super.submitList(timeCategoryList) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return viewHolderFactory.create(parent, viewType)
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

    override fun onItemClick(position: Int) = invokeIfHasId(position, onItemClickCallback)

    override fun onSwipeLeft(position: Int) = invokeIfHasId(position, onItemRemoveCallback)

    override fun onSwipeRight(position: Int) = invokeIfHasId(position, onItemDeactivateCallback)

    private fun initList(dataList: List<TimeHolder>?): MutableList<TimeHolder>? {
        if (dataList == cachedDataList) return null
        return dataList?.let { categorizeList(it) }
    }

    private fun categorizeList(dataList: List<TimeHolder>): MutableList<TimeHolder> {
        return dataList.toMutableList()
            .apply { addAll(timeCategoryList) }
            .sortedWith(compareBy<TimeHolder> { it.time }.thenBy { !it.sortPriority })
            .toMutableList()
    }

    private fun createCategoriesData(formatter: DateTimeFormatter): List<TimeCategory> {
        val categories = mutableListOf<TimeCategory>()
        for (hour in 0..23) {
            val time = LocalTime.of(hour, 0)
            categories.add(TimeCategory(time, formatter))
        }
        return categories
    }

    private data class TimeCategory(
        override val time: LocalTime,
        private val timeFormatter: DateTimeFormatter,
        override val id: String? = null
    ) : TimeHolder(timeFormatter) {
        override val viewType = TIME_CATEGORY_VIEW
        override val sortPriority = true

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

    private fun TimeHolderAdapter.invokeIfHasId(position: Int, callback: (id: String) -> Unit) {
        getItem(position).id?.let(callback::invoke)
    }
}
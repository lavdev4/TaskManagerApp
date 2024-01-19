package com.example.taskmanagerapp.presentation.adapters.factories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.databinding.ItemTasksListBinding
import com.example.taskmanagerapp.presentation.adapters.TimeHolderAdapter
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import com.example.taskmanagerapp.presentation.views.TextSeparatorView

class TimeViewHolderFactory {

    class TimeCategoryViewHolder(
        private val view: TextSeparatorView,
    ) : ViewHolder(view) {
        var text: String?
            set(value) { view.viewText = value }
            get() = view.viewText
    }

    class TaskDataViewHolder(
        val binding: ItemTasksListBinding
    ) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {  }
        }
    }

    companion object{

        fun create(parent: ViewGroup, viewType: Int): ViewHolder {
            return when (viewType) {

                TimeHolder.TIME_CATEGORY_VIEW -> {
                    val view = TextSeparatorView(parent.context)
                        .apply {
                            id = View.generateViewId()
                            layoutParams =
                                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                            viewTextHorizontalPosition = TextSeparatorView.TEXT_HORIZONTAL_LEFT
                            viewTextVerticalPosition = TextSeparatorView.TEXT_VERTICAL_CENTER
                        }
                    TimeCategoryViewHolder(view)
                }

                TimeHolder.TASK_DATA_VIEW -> {
                    val binding = ItemTasksListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    TaskDataViewHolder(binding)
                }

                else -> throw RuntimeException("Unknown view type: $viewType")
            }
        }
    }
}
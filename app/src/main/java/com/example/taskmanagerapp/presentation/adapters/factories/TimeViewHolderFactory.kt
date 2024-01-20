package com.example.taskmanagerapp.presentation.adapters.factories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.databinding.ItemDisabledTasksListBinding
import com.example.taskmanagerapp.databinding.ItemTasksListBinding
import com.example.taskmanagerapp.presentation.adapters.callbacks.ItemClickConsumer
import com.example.taskmanagerapp.presentation.adapters.callbacks.ItemSwipeCallback
import com.example.taskmanagerapp.presentation.adapters.callbacks.ItemSwipeConsumer
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import com.example.taskmanagerapp.presentation.views.TextSeparatorView

class TimeViewHolderFactory private constructor(builder: Builder) {
    private var itemClickCallback = builder.itemClickedCallback
    private var itemSwipeCallback = builder.itemSwipedCallback

    object Builder {
        var itemClickedCallback: ItemClickConsumer? = null
            private set
        var itemSwipedCallback: ItemSwipeConsumer? = null
            private set

        fun clickCallback(callback: ItemClickConsumer): Builder {
            this.itemClickedCallback = callback
            return this
        }

        fun swipeCallback(callback: ItemSwipeConsumer): Builder {
            this.itemSwipedCallback = callback
            return this
        }

        fun build(): TimeViewHolderFactory {
            return TimeViewHolderFactory(this)
        }
    }

    inner class TimeCategoryViewHolder(
        private val view: TextSeparatorView,
    ) : ViewHolder(view) {
        var text: String?
            set(value) { view.viewText = value }
            get() = view.viewText
    }

    inner class TaskDataViewHolder(
        val binding: ItemTasksListBinding,
    ) : ViewHolder(binding.root), ItemSwipeCallback.SwipeViewHolder {

        init {
            itemClickCallback?.let { callback ->
                binding.root.setOnClickListener {
                    invokeIfPosition(callback::onItemClick)
                }
            }
        }

        override fun onSwipeLeft() {
            itemSwipeCallback?.let { callback ->
                invokeIfPosition(callback::onSwipeLeft)
            }
        }

        override fun onSwipeRight() {
            itemSwipeCallback?.let { callback ->
                invokeIfPosition(callback::onSwipeRight)
            }
        }
    }

    inner class DisabledTDViewHolder(
        val binding: ItemDisabledTasksListBinding,
    ) : ViewHolder(binding.root), ItemSwipeCallback.SwipeLeftViewHolder {

        init {
            itemClickCallback?.let { callback ->
                binding.root.setOnClickListener {
                    invokeIfPosition(callback::onItemClick)
                }
            }
        }

        override fun onSwipe() {
            itemSwipeCallback?.let { callback ->
                invokeIfPosition(callback::onSwipeLeft)
            }
        }
    }

    fun create(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {

            TimeHolder.TIME_CATEGORY_VIEW -> {
                val view = TextSeparatorView(parent.context).apply {
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

            TimeHolder.DISABLED_TASK_DATA_VIEW -> {
                val binding = ItemDisabledTasksListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DisabledTDViewHolder(binding)
            }

            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    private fun ViewHolder.invokeIfPosition(callback: (position: Int) -> Unit) {
        val position = adapterPosition
        if (position != NO_POSITION) callback.invoke(position)
    }
}
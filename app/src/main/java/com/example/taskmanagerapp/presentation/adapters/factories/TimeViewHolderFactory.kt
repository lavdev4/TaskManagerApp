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
    private var orientationPortrait = builder.orientationPortrait

    object Builder {
        var itemClickedCallback: ItemClickConsumer? = null
            private set
        var itemSwipedCallback: ItemSwipeConsumer? = null
            private set
        var orientationPortrait: Boolean? = null

        fun clickCallback(callback: ItemClickConsumer): Builder {
            this.itemClickedCallback = callback
            return this
        }

        fun swipeCallback(callback: ItemSwipeConsumer): Builder {
            this.itemSwipedCallback = callback
            return this
        }

        fun orientationPortrait(portrait: Boolean): Builder {
            this.orientationPortrait = portrait
            return this
        }

        fun build(): TimeViewHolderFactory {
            return TimeViewHolderFactory(this)
        }
    }

    fun create(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TimeHolder.TIME_CATEGORY_VIEW -> createTimeCategoryViewHolder(parent)
            TimeHolder.TASK_DATA_VIEW -> createTaskDataViewHolder(parent)
            TimeHolder.DISABLED_TASK_DATA_VIEW -> createDisabledTDViewHolder(parent)
            else -> throw RuntimeException("Unknown view type: $viewType")
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
    ) : ViewHolder(binding.root), ItemSwipeCallback.SwipeRightViewHolder {

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

    private fun ViewHolder.invokeIfPosition(callback: (position: Int) -> Unit) {
        val position = adapterPosition
        if (position != NO_POSITION) callback.invoke(position)
    }

    private fun createTimeCategoryViewHolder(parent: ViewGroup): ViewHolder {
        val view = TextSeparatorView(parent.context).apply {
            id = View.generateViewId()
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            if (orientationPortrait == true) {
                viewTextHorizontalPosition = TextSeparatorView.TEXT_HORIZONTAL_LEFT
                viewTextVerticalPosition = TextSeparatorView.TEXT_VERTICAL_CENTER
            } else {
                viewTextHorizontalPosition = TextSeparatorView.TEXT_HORIZONTAL_RIGHT
                viewTextVerticalPosition = TextSeparatorView.TEXT_VERTICAL_TOP
            }
        }
        return TimeCategoryViewHolder(view)
    }

    private fun createTaskDataViewHolder(parent: ViewGroup): ViewHolder {
        val binding = ItemTasksListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskDataViewHolder(binding)
    }

    private fun createDisabledTDViewHolder(parent: ViewGroup): ViewHolder {
        val binding = ItemDisabledTasksListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DisabledTDViewHolder(binding)
    }
}
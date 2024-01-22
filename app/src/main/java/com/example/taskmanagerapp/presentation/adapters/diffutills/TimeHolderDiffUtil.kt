package com.example.taskmanagerapp.presentation.adapters.diffutills

import androidx.recyclerview.widget.DiffUtil
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder

class TimeHolderDiffUtil : DiffUtil.ItemCallback<TimeHolder>() {

    override fun areItemsTheSame(oldItem: TimeHolder, newItem: TimeHolder): Boolean {
        return oldItem.compareItems(newItem)
    }

    override fun areContentsTheSame(oldItem: TimeHolder, newItem: TimeHolder): Boolean {
        return oldItem.compareContents(newItem)
    }
}
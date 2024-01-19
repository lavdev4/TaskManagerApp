package com.example.taskmanagerapp.presentation.adapters.diffutills

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.taskmanagerapp.presentation.adapters.TimeHolderAdapter
import com.example.taskmanagerapp.presentation.adapters.models.TaskData
import com.example.taskmanagerapp.presentation.adapters.models.TimeHolder
import com.example.taskmanagerapp.presentation.mappers.TimeHolderMapper

class TimeHolderDiffUtil : DiffUtil.ItemCallback<TimeHolder>() {

    override fun areItemsTheSame(oldItem: TimeHolder, newItem: TimeHolder): Boolean {
        return oldItem.compareItems(newItem)
    }

    override fun areContentsTheSame(oldItem: TimeHolder, newItem: TimeHolder): Boolean {
        return oldItem.compareContents(newItem)
    }
}
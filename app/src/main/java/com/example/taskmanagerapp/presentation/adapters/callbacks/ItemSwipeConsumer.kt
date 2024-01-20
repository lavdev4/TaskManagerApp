package com.example.taskmanagerapp.presentation.adapters.callbacks

interface ItemSwipeConsumer {
    fun onSwipeLeft(position: Int)
    fun onSwipeRight(position: Int)
}
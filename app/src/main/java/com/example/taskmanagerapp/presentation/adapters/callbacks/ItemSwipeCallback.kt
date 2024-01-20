package com.example.taskmanagerapp.presentation.adapters.callbacks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemSwipeCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        val swipeFlags = when (viewHolder) {
            is SwipeLeftViewHolder -> ItemTouchHelper.START
            is SwipeViewHolder -> ItemTouchHelper.START or ItemTouchHelper.END
            else -> 0
        }
        return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.START -> when (viewHolder) {
                is SwipeViewHolder -> (viewHolder as SwipeViewHolder).onSwipeLeft()
                is SwipeLeftViewHolder -> (viewHolder as SwipeLeftViewHolder).onSwipe()
            }
            ItemTouchHelper.END -> (viewHolder as SwipeViewHolder).onSwipeRight()
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
            val itemView = viewHolder.itemView
            if (dX < 0) {
                val paint = Paint().apply { color = Color.RED }
                val rect = Rect(itemView.left, itemView.top, itemView.right, itemView.bottom)
                canvas.drawRect(rect, paint)
            } else {
                val paint = Paint().apply { color = Color.BLUE }
                val rect = Rect(itemView.left, itemView.top, itemView.right, itemView.bottom)
                canvas.drawRect(rect, paint)
            }
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
    }

    interface SwipeViewHolder {
        fun onSwipeLeft()
        fun onSwipeRight()
    }

    interface SwipeLeftViewHolder {
        fun onSwipe()
    }
}
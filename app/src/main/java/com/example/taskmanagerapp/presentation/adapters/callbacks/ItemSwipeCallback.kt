package com.example.taskmanagerapp.presentation.adapters.callbacks

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class ItemSwipeCallback(
    private val checkIcon: Drawable,
    private val deleteIcon: Drawable,
    checkBackgroundColor: Int,
    deleteBackgroundColor: Int,
) : ItemTouchHelper.Callback() {
    private val deleteBackgroundPaint = Paint().apply { color = deleteBackgroundColor }
    private val checkBackgroundPaint = Paint().apply { color = checkBackgroundColor }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        val swipeFlags = when (viewHolder) {
            is SwipeRightViewHolder -> ItemTouchHelper.START
            is SwipeViewHolder -> ItemTouchHelper.START or ItemTouchHelper.END
            else -> 0
        }
        return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.START -> when (viewHolder) {
                is SwipeViewHolder -> (viewHolder as SwipeViewHolder).onSwipeLeft()
                is SwipeRightViewHolder -> (viewHolder as SwipeRightViewHolder).onSwipe()
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
        isCurrentlyActive: Boolean,
    ) {
        val maxTranslationX = dX * ITEM_SWIPE_OFFSET_PERCENT
        super.onChildDraw(
            canvas,
            recyclerView,
            viewHolder,
            maxTranslationX,
            dY,
            actionState,
            isCurrentlyActive
        )
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && maxTranslationX != 0f) {
            val view = viewHolder.itemView
            val cornerOffset = (view.width * 0.04f).toInt()
            val absMaxTranslationX = abs(maxTranslationX).toInt()
            if (maxTranslationX < 0) {
                val left = view.right - absMaxTranslationX - cornerOffset
                val field = Rect(left, view.top, view.right, view.bottom)
                drawLeftBackground(canvas, field)
                drawLeftIcon(canvas, field, absMaxTranslationX)
            } else {
                val right = view.left + absMaxTranslationX + cornerOffset
                val field = Rect(view.left, view.top, right, view.bottom)
                drawRightBackground(canvas, field)
                drawRightIcon(canvas, field, absMaxTranslationX)
            }
        }
    }

    private fun drawLeftBackground(canvas: Canvas, field: Rect) {
        canvas.drawRect(field, deleteBackgroundPaint)
    }

    private fun drawRightBackground(canvas: Canvas, field: Rect) {
        canvas.drawRect(field, checkBackgroundPaint)
    }

    private fun drawLeftIcon(canvas: Canvas, field: Rect, maxWidth: Int) {
        val intrinsicWidth = deleteIcon.intrinsicWidth
        val intrinsicHeight = deleteIcon.intrinsicHeight
        val horizontalMargin = maxWidth / 4
        val verticalMargin = (field.height() - intrinsicHeight) / 2
        val left = field.right - intrinsicWidth - horizontalMargin
        val right = field.right - horizontalMargin
        val top = field.top + verticalMargin
        val bottom = field.bottom - verticalMargin
        deleteIcon.setBounds(left, top, right, bottom)
        deleteIcon.draw(canvas)
    }

    private fun drawRightIcon(canvas: Canvas, field: Rect, maxWidth: Int) {
        val intrinsicWidth = checkIcon.intrinsicWidth
        val intrinsicHeight = checkIcon.intrinsicHeight
        val horizontalMargin = maxWidth / 4
        val verticalMargin = (field.height() - intrinsicHeight) / 2
        val left = field.left + horizontalMargin
        val right = field.left + intrinsicWidth + horizontalMargin
        val top = field.top + verticalMargin
        val bottom = field.bottom - verticalMargin
        checkIcon.setBounds(left, top, right, bottom)
        checkIcon.draw(canvas)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 1f
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return super.getSwipeVelocityThreshold(defaultValue * ITEM_SWIPE_OFFSET_PERCENT)
    }

    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float,
    ): Long {
        if (animationType == ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS) {
            return SWIPE_SUCCESS_ANIMATION_DURATION
        }
        if (animationType == ItemTouchHelper.ANIMATION_TYPE_SWIPE_CANCEL) {
            return SWIPE_CANCEL_ANIMATION_DURATION
        }
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
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

    interface SwipeRightViewHolder {
        fun onSwipe()
    }

    companion object {
        private const val ITEM_SWIPE_OFFSET_PERCENT = 0.2f
        private const val SWIPE_SUCCESS_ANIMATION_DURATION = 450L
        private const val SWIPE_CANCEL_ANIMATION_DURATION = 300L
    }
}
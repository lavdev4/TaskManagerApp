package com.example.taskmanagerapp.presentation.adapters.decorators

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdaptiveSpacingItemDecorator(
    /** Value between 0.0f and 1.0f representing percent from item height/width
     * that will be set as a spacing between items.
     * For example, for vertical orientation items spacing will be obtained like:
     * parent.height / itemCount * [itemOffsetPercent]. */
    private var itemOffsetPercent: Float,
    /** First and last items. */
    private val outerItemsOffsetDp: Float
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        if (itemOffsetPercent < 0f || itemOffsetPercent > 1f) { itemOffsetPercent = 0f }

        val orientation = (parent.layoutManager as LinearLayoutManager).orientation
        val itemCount = state.itemCount
        val parentLength = when (orientation) {
            RecyclerView.VERTICAL -> parent.height
            RecyclerView.HORIZONTAL -> parent.width
            else -> 0
        }
        val offset = parentLength / itemCount * itemOffsetPercent

        val position = parent.getChildAdapterPosition(view)
        val firstPosition = 0
        val lastPosition = itemCount - 1

        val topOffset = if (position == firstPosition) {
            convertDpToPixel(outerItemsOffsetDp, parent.context)
        } else offset
        val bottomOffset = if (position == lastPosition) {
            convertDpToPixel(outerItemsOffsetDp, parent.context)
        } else offset

        when (orientation) {
            RecyclerView.VERTICAL -> {
                outRect.top = topOffset.toInt()
                outRect.bottom = bottomOffset.toInt()
            }
            RecyclerView.HORIZONTAL -> {
                outRect.left = topOffset.toInt()
                outRect.right = bottomOffset.toInt()
            }
        }
    }

    private fun convertDpToPixel(dp: Float, context: Context): Float {
        val displayPixelDensity = context.resources.displayMetrics.densityDpi
        return dp * (displayPixelDensity.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
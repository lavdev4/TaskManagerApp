package com.example.taskmanagerapp.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.os.Build
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.example.taskmanagerapp.R
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class TextSeparatorView @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null
) : View(context, attributesSet) {

    private var _viewDividerColor by Delegates.notNull<Int>()
    var viewDividerColor: Int
        set(value) {
            _viewDividerColor = value
            initPaint()
            invalidate()
        }
        get() = _viewDividerColor

    private var _viewDividerWidth by Delegates.notNull<Float>()
    var viewDividerWidthDp: Float
        set(value) {
            _viewDividerWidth = dpToPixel(value)
            requestLayout()
        }
        get() = pixelToDp(_viewDividerWidth)

    private var _viewTextColor by Delegates.notNull<Int>()
    var viewTextColor: Int
        set(value) {
            _viewTextColor = value
            initPaint()
            invalidate()
        }
        get() = _viewTextColor

    private var _viewTextSize by Delegates.notNull<Float>()
    var viewTextSizeSp: Float
        set(value) {
            _viewTextSize = spToPixel(value)
            initPaint()
            setTextDimens(width.toFloat())
            requestLayout()
        }
        get() = pixelToSp(_viewTextSize)

    private var _viewTextHorizontalPosition by Delegates.notNull<Int>()
    var viewTextHorizontalPosition: Int
        set(value) {
            _viewTextHorizontalPosition = value
            requestLayout()
        }
        get() = _viewTextHorizontalPosition

    private var _viewTextVerticalPosition by Delegates.notNull<Int>()
    var viewTextVerticalPosition: Int
        set(value) {
            _viewTextVerticalPosition = value
            requestLayout()
        }
        get() = _viewTextVerticalPosition

    private lateinit var linePaint: Paint
    private lateinit var textPaint: Paint

    private var _viewText: String? = null
    var viewText: String?
        set(value) {
            cachedText = value
            setTextDimens(width.toFloat())
            requestLayout()
        }
        get() = _viewText
    private var cachedText: String? = null
    private var viewTextWidth by Delegates.notNull<Float>()
    private var viewTextHeight by Delegates.notNull<Float>()
    private val textOffset = dpToPixel(TEXT_LINE_OFFSET)

    init {
        attributesSet?.let { initAttrs(it) } ?: run { initDefaultAttrs() }
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = calculateViewMinimumHeight()
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min((desiredHeight + 0.5f).toInt(), heightSize)
            else -> desiredHeight
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setTextDimens(w.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLine(canvas)
        _viewText?.let { drawText(canvas, it) }
    }

    private fun drawLine(canvas: Canvas) {
        if (!_viewText.isNullOrEmpty() && _viewTextVerticalPosition == TEXT_VERTICAL_CENTER) {
            drawTextSeparatedLine(canvas)
        } else {
            drawSimpleLine(canvas)
        }
    }

    private fun drawTextSeparatedLine(canvas: Canvas) {
        val screenCenterY = height / 2f
        if (_viewTextHorizontalPosition != TEXT_HORIZONTAL_CENTER) {
            val startX = when (_viewTextHorizontalPosition) {
                TEXT_HORIZONTAL_LEFT -> viewTextWidth + 2f * textOffset
                TEXT_HORIZONTAL_RIGHT -> 0f
                else -> throw RuntimeException("Unsupported text position")
            }
            val endX = when (_viewTextHorizontalPosition) {
                TEXT_HORIZONTAL_LEFT -> width.toFloat()
                TEXT_HORIZONTAL_RIGHT -> width - viewTextWidth - 2f * textOffset
                else -> throw RuntimeException("Unsupported text position")
            }
            canvas.drawLine(startX, screenCenterY, endX, screenCenterY, linePaint)
        } else {
            val firstStartX = 0f
            val firstEndX = width / 2f - viewTextWidth / 2f - textOffset
            val secondStartX = width / 2f + viewTextWidth / 2f + textOffset
            val secondEndX = width.toFloat()
            canvas.drawLine(firstStartX, screenCenterY, firstEndX, screenCenterY, linePaint)
            canvas.drawLine(secondStartX, screenCenterY, secondEndX, screenCenterY, linePaint)
        }
    }

    private fun drawSimpleLine(canvas: Canvas) {
        val screenCenterY = height / 2f
        canvas.drawLine(
            0f,
            screenCenterY,
            width.toFloat(),
            screenCenterY,
            linePaint
        )
    }

    private fun drawText(canvas: Canvas, text: String) {
        val screenCenterX = width / 2f
        val screenCenterY = height / 2f
        val x = when (_viewTextHorizontalPosition) {
            TEXT_HORIZONTAL_LEFT -> 0f + textOffset
            TEXT_HORIZONTAL_CENTER -> screenCenterX - viewTextWidth / 2f
            TEXT_HORIZONTAL_RIGHT -> width - viewTextWidth - textOffset
            else -> throw RuntimeException("Unsupported text position")
        }
        val y = when (_viewTextVerticalPosition) {
            TEXT_VERTICAL_TOP -> screenCenterY - textOffset - _viewDividerWidth / 2f
            TEXT_VERTICAL_CENTER -> screenCenterY + viewTextHeight / 2f
            TEXT_VERTICAL_BOTTOM -> screenCenterY + viewTextHeight + _viewDividerWidth / 2f + textOffset
            else -> throw RuntimeException("Unsupported text position")
        }
        canvas.drawText(text, x, y, textPaint)
    }

    private fun initAttrs(attributesSet: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attributesSet, R.styleable.TextSeparatorView)
        with(typedArray) {
            _viewDividerColor = getColor(
                R.styleable.TextSeparatorView_divider_color,
                DEFAULT_DIVIDER_COLOR
            )
            _viewDividerWidth = getDimension(
                R.styleable.TextSeparatorView_divider_width,
                dpToPixel(DEFAULT_DIVIDER_WIDTH)
            )
            _viewText = getString(R.styleable.TextSeparatorView_text)
            _viewTextColor = getInt(R.styleable.TextSeparatorView_text_color, DEFAULT_TEXT_COLOR)
            _viewTextSize = getDimension(
                R.styleable.TextSeparatorView_text_size,
                spToPixel(DEFAULT_TEXT_SIZE)
            )
            _viewTextHorizontalPosition = getInt(
                R.styleable.TextSeparatorView_text_horizontal_position,
                DEFAULT_TEXT_HORIZONTAL_POSITION
            )
            _viewTextVerticalPosition = getInt(
                R.styleable.TextSeparatorView_text_vertical_position,
                DEFAULT_TEXT_VERTICAL_POSITION
            )
        }
        typedArray.recycle()
    }

    private fun initDefaultAttrs() {
        _viewDividerColor = DEFAULT_DIVIDER_COLOR
        _viewDividerWidth = dpToPixel(DEFAULT_DIVIDER_WIDTH)
        _viewText = null
        _viewTextColor = DEFAULT_TEXT_COLOR
        _viewTextSize = spToPixel(DEFAULT_TEXT_SIZE)
        _viewTextHorizontalPosition = DEFAULT_TEXT_HORIZONTAL_POSITION
        _viewTextVerticalPosition = DEFAULT_TEXT_VERTICAL_POSITION
    }

    private fun initPaint() {
        textPaint = TextPaint().apply {
            color = _viewTextColor
            textSize = _viewTextSize
        }
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = _viewDividerColor
            style = Paint.Style.STROKE
            strokeWidth = _viewDividerWidth
        }
    }

    private fun setTextDimens(viewWidth: Float) {
        cachedText?.let { text ->
            val textWidth = getTextWidth(text)
            val trimmedText = trimText(
                text,
                textWidth,
                viewWidth * MAX_TEXT_WIDTH_PERCENT,
                textPaint
            )
            _viewText = trimmedText
            viewTextWidth = textPaint.measureText(trimmedText)
            viewTextHeight = getTextHeight(textPaint.fontMetrics)
        }
    }

    private fun getTextWidth(text: String): Float {
        return textPaint.measureText(text)
    }

    private fun getTextHeight(fontMetrics: FontMetrics): Float {
        return with(fontMetrics) { top.absoluteValue - bottom }
    }

    private fun getTextLineHeight(fontMetrics: FontMetrics): Float {
        return with(fontMetrics) { top.absoluteValue + bottom }
    }

    private fun trimText(
        text: String,
        textWidth: Float,
        maxWidth: Float,
        textPaint: Paint,
    ): String {
        val measuredWidth = floatArrayOf(textWidth)
        if (textWidth > maxWidth) {
            val trimmedLength = textPaint.breakText(
                text,
                true,
                maxWidth,
                measuredWidth
            )
            return text.dropLast(text.length - trimmedLength + 3)
                .plus("...")
        }
        return text
    }

    private fun calculateViewMinimumHeight(): Float {
        return if (_viewText != null) {
            if (_viewTextVerticalPosition == TEXT_VERTICAL_CENTER) {
                max(_viewDividerWidth, getTextLineHeight(textPaint.fontMetrics))
            } else {
                2f * (_viewDividerWidth + getTextLineHeight(textPaint.fontMetrics) + textOffset)
            }
        } else {
            _viewDividerWidth
        }
    }

    private fun dpToPixel(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    private fun spToPixel(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
    }

    private fun pixelToDp(px: Float): Float {
        return px / resources.displayMetrics.density
    }

    @Suppress("DEPRECATION")
    private fun pixelToSp(px: Float): Float {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            TypedValue.deriveDimension(TypedValue.COMPLEX_UNIT_PX, px, resources.displayMetrics)
        } else {
            px / resources.displayMetrics.scaledDensity
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return SavedState(
            superState,
            _viewDividerColor,
            _viewDividerWidth,
            _viewTextColor,
            _viewTextSize,
            _viewTextHorizontalPosition,
            _viewTextVerticalPosition,
            cachedText
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        _viewDividerColor = state.dividerColor
        _viewDividerWidth = state.dividerWidth
        _viewTextColor = state.textColor
        _viewTextSize = state.textSize
        _viewTextHorizontalPosition = state.textHorizontalPosition
        _viewTextVerticalPosition = state.textVerticalPosition
        cachedText = state.cachedText
        initPaint()
        setTextDimens(width.toFloat())
    }

    private data class SavedState(
        val baseState: Parcelable?,
        val dividerColor: Int,
        val dividerWidth: Float,
        val textColor: Int,
        val textSize: Float,
        val textHorizontalPosition: Int,
        val textVerticalPosition: Int,
        val cachedText: String?,
    ) : BaseSavedState(baseState)

    companion object {
        /** Values in dp/sp */
        private const val TEXT_LINE_OFFSET = 5f
        private const val MAX_TEXT_WIDTH_PERCENT = 0.8f

        const val TEXT_HORIZONTAL_LEFT = 0
        const val TEXT_HORIZONTAL_CENTER = 1
        const val TEXT_HORIZONTAL_RIGHT = 2
        const val TEXT_VERTICAL_TOP = 3
        const val TEXT_VERTICAL_CENTER = 4
        const val TEXT_VERTICAL_BOTTOM = 5

        private val DEFAULT_DIVIDER_COLOR = Color.argb(80, 0, 0, 0)
        private val DEFAULT_TEXT_COLOR = Color.argb(140, 0, 0, 0)
        private val DEFAULT_DIVIDER_WIDTH = 1f
        private val DEFAULT_TEXT_SIZE = 12f
        private val DEFAULT_TEXT_HORIZONTAL_POSITION = TEXT_HORIZONTAL_LEFT
        private val DEFAULT_TEXT_VERTICAL_POSITION = TEXT_VERTICAL_TOP
    }
}